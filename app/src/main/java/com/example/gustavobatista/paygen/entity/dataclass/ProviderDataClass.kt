package com.example.gustavobatista.paygen.entity.dataclass

import com.example.gustavobatista.paygen.entity.Provider

class ProviderDataClass private constructor() {

    companion object {

        internal val instance = ProviderDataClass()
        var provider: Provider? = null
    }
}
