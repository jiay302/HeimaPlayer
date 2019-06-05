package com.example.xuyue.heimaplayer.net

import com.example.xuyue.heimaplayer.model.MvPagerBean
import com.example.xuyue.heimaplayer.util.URLProviderUtils

/**
 * @Date 2019/5/23 2:56 PM
 * @Author xuyue
 * @Description TODO
 */
class MvListRequest(type: Int, code: String, offset: Int, handler: ResponseHandler<MvPagerBean>) :
    MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code, offset, 20), handler)