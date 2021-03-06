package com.skydoves.themovies.view.ui.details.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.repository.TvRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class TvDetailViewModel @Inject
constructor(private val repository: TvRepository) : ViewModel() {

  private val keywordIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val keywordListLiveData: LiveData<Resource<List<Keyword>>>

  private val videoIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val videoListLiveData: LiveData<Resource<List<Video>>>

  private val reviewIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val reviewListLiveData: LiveData<Resource<List<Review>>>

  init {
    Timber.d("Injection TvDetailViewModel")

    keywordListLiveData = keywordIdLiveData.switchMap {
      keywordIdLiveData.value?.let { repository.loadKeywordList(it) }
          ?: AbsentLiveData.create()
    }

    videoListLiveData = videoIdLiveData.switchMap {
      videoIdLiveData.value?.let { repository.loadVideoList(it) } ?: AbsentLiveData.create()
    }

    reviewListLiveData = reviewIdLiveData.switchMap {
      reviewIdLiveData.value?.let { repository.loadReviewsList(it) }
          ?: AbsentLiveData.create()
    }
  }

  fun getKeywordListObservable() = keywordListLiveData
  fun postKeywordId(id: Int) = keywordIdLiveData.postValue(id)

  fun getVideoListObservable() = videoListLiveData
  fun postVideoId(id: Int) = videoIdLiveData.postValue(id)

  fun getReviewListObservable() = reviewListLiveData
  fun postReviewId(id: Int) = reviewIdLiveData.postValue(id)
}
