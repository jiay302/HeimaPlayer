package com.example.xuyue.heimaplayer.net

import com.example.xuyue.heimaplayer.util.URLProviderUtils
import com.itheima.player.model.bean.HomeItemBean

/**
 * @Date 2019/5/21 9:42 PM
 * @Author xuyue
 * @Description 首页数据请求类
 */
class HomeRequest(type: Int, offset: Int, handler: ResponseHandler<List<HomeItemBean>>) :
    MRequest<List<HomeItemBean>>(type, URLProviderUtils.getHomeUrl(offset, 20), handler)