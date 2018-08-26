package com.example.gustavobatista.paygen.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.CloudinaryResponse
import com.example.gustavobatista.paygen.entity.dto.LoginDTO
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.CustomerService
import com.example.gustavobatista.paygen.service.ImageService
import com.example.gustavobatista.paygen.util.ImageUtil
import com.example.gustavobatista.paygen.util.ImageUtil.load
import com.example.gustavobatista.paygen.util.PermissionUtils
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class ProfileActivity : BaseActivity() {
    private lateinit var picture: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupActionBar()
        profileImage.load(prefs.picture) { request ->
            request.resize(480, 480).centerCrop()
        }
        picture = prefs.picture
        etName.setText(prefs.userName)
        profileImage.setOnClickListener { changePic() }
        btPhoto.setOnClickListener { changePic() }
        btSave.setOnClickListener { saveProfile() }
    }


    override fun onStart() {
        super.onStart()

        if (!PermissionUtils.checkCameraStoragePermission(getActivity())) {
            PermissionUtils.requestCameraStoragePermission(getActivity())
        }
    }

    private fun changePic() {
        EasyImage.openChooserWithGallery(getActivity(), getString(R.string.title_picker), 0)
    }

    private fun saveProfile() {
        if (etName.text.isNullOrEmpty()) {
            showWarning(getString(R.string.warning_empty_name))
            return
        }

        val dto = LoginDTO()
        dto.userId = prefs.userId
        dto.picture = picture
        dto.userName = etName.text.toString()
        name = dto.userName

        showProgress()
        CustomerService.updateProfile(dto).applySchedulers().subscribe(
                {
                    prefs.picture = picture
                    prefs.userName = name
                    showMessage(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun getImageAsString(file: File) {
        showProgress()
        ImageUtil.compressImage(file).applySchedulers().subscribe(
                {
                    uploadImage(it)
                },
                {
                    closeProgress()
                    handleException(it)
                }
        )
    }


    private fun uploadImage(file: String) {
        ImageService.uploadImage(file).applySchedulers().subscribe(
                {
                    setupImages(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun setupImages(response: CloudinaryResponse) {
        picture = response.url!!
        profileImage.load(response.url) { request ->
            request.resize(480, 480).centerCrop()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                if (e != null) {
                    showWarning(e.message!!)
                }
            }

            override fun onImagesPicked(files: MutableList<File>, p1: EasyImage.ImageSource?, type: Int) {
                getImageAsString(files[0])
            }

            override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                Log.d("EasyImage", "cancelled")
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            alert(getString(R.string.disclaimer_camera_usage),
                    getString(R.string.error_title)) {
                yesButton {
                    PermissionUtils.requestCameraPermission(getActivity())
                }
            }.show()
        }
    }
}
