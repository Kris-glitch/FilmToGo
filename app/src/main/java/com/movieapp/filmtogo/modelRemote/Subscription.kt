package com.movieapp.filmtogo.modelRemote

class Subscription(
    val sub_title: String = "",
    val sub_price: String = "",
    val sub_desc_one: String = "",
    val sub_desc_two: String = "",
    val sub_desc_three: String = ""
) {
    companion object {
        val SUBSCRIPTION_BASIC = Subscription(
            "Basic",
            "$10.99/month",
            "Stream movies and TV shows in standard definition (SD)",
            "Access a limited library of movies and TV shows for streaming",
            "Download up to 5 movies or episodes for offline viewing"
        )

        val SUBSCRIPTION_STANDARD = Subscription(
            "Standard",
            "$15.99/month",
            "Stream movies and TV shows in high definition (HD)",
            "Access an extensive library of movies and TV shows for streaming",
            "Download up to 10 movies or episodes for offline viewing"
        )

        val SUBSCRIPTION_PREMIUM = Subscription(
            "Premium",
            "$19.99/month",
            "Stream movies and TV shows in ultra high definition (UHD/4K) where available",
            "Access our full library of movies and TV shows, including exclusive content",
            "Download up to 15 movies or episodes for offline viewing"
        )
    }
}