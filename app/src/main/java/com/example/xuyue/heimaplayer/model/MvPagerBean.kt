package com.example.xuyue.heimaplayer.model

data class MvPagerBean(var totalCount: Int, var videos: List<VideosBean>)
data class VideosBean(
        var id: Int, var title: String, var description: String, var artistName: String,
        var posterPic: String, var thumbnailPic: String, var albumImg: String, var regdate: String,
        var videoSourceTypeName: String, var totalViews: Int, var totalPcViews: Int,
        var totalMobileViews: Int, var totalComments: Int, var url: String,
        var hdUrl: String, var uhdUrl: String, var shdUrl: String, var videoSize: Int,
        var hdVideoSize: Int,var uhdVideoSize: Int, var shdVideoSize: Int, var duration: Int,
        var status: Int, var linkId: Int , var playListPic: String,var artists: List<ArtistsBean>)
data class ArtistsBean(var artistId: Int, var artistName: String)
