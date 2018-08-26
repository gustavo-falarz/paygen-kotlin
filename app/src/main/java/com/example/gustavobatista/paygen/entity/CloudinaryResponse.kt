package com.example.gustavobatista.paygen.entity


class CloudinaryResponse {
    var tags: Array<String>? = null

    var bytes: String? = null

    var etag: String? = null

    var width: String? = null

    var public_id: String? = null

    var format: String? = null

    var type: String? = null

    var original_filename: String? = null

    var url: String? = null

    var version: String? = null

    var height: String? = null

    var resource_type: String? = null

    var created_at: String? = null

    var signature: String? = null

    var secure_url: String? = null

    override fun toString(): String {
        return "ClassPojo [tags = $tags, bytes = $bytes, etag = $etag, width = $width, public_id = $public_id, format = $format, type = $type, original_filename = $original_filename, url = $url, version = $version, height = $height, resource_type = $resource_type, created_at = $created_at, signature = $signature, secure_url = $secure_url]"
    }
}