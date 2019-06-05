package com.example.xuyue.heimaplayer.net

import com.example.xuyue.heimaplayer.model.YueDanBean
import com.example.xuyue.heimaplayer.util.URLProviderUtils

/**
 * @Date 2019/5/22 10:58 PM
 * @Author xuyue
 * @Description 悦单网络请求类
 */
class YueDanRequest(type: Int, offset: Int, handler: ResponseHandler<YueDanBean>) :
    MRequest<YueDanBean>(type, URLProviderUtils.getYueDanUrl(offset, 20), handler)