package com.prabal.animatorlearn.models

data class AnimieDetailModel(
    val `data`: AnimieData
)

data class AnimieData(
    val aired: Aired,
    val airing: Boolean,
    val approved: Boolean,
    val background: String,
    val broadcast: BroadcastX,
    val demographics: List<DemographicX>,
    val duration: String,
    val episodes: Int,
    val explicit_genres: List<Any?>,
    val favorites: Int,
    val genres: List<GenreX>,
    val images: ImagesXX,
    val licensors: List<LicensorX>,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<ProducerX>,
    val rank: Int,
    val rating: String,
    val score: Double,
    val scored_by: Int,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<StudioX>,
    val synopsis: String,
    val themes: List<ThemeX>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val titles: List<TitleX>,
    val trailer: TrailerX,
    val type: String,
    val url: String,
    val year: Int
)

data class DemographicX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)


data class GenreX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class LicensorX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class ProducerX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class StudioX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class ThemeX(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class TrailerX(
    val embed_url: String,
    val images: ImagesXXX,
    val url: String,
    val youtube_id: String
)