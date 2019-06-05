package com.example.xuyue.heimaplayer.net

import com.example.xuyue.heimaplayer.model.MvAreaBean
import com.example.xuyue.heimaplayer.util.URLProviderUtils

/**
 * @Date 2019/5/23 1:40 PM
 * @Author xuyue
 * @Description MvFragment顶部tab数据请求
 */
class MvAreaRequest(handler: ResponseHandler<List<MvAreaBean>>) :
    MRequest<List<MvAreaBean>>(0, URLProviderUtils.getMVareaUrl(), handler)