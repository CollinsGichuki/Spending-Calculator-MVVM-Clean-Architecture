package dev.ronnie.spendingcalculator.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.spendingcalculator.data.AddTag
import dev.ronnie.spendingcalculator.utils.Event
import dev.ronnie.spendingcalculator.data.SmsRepository
import kotlinx.coroutines.launch


class AddTagViewModel(private val smsRepository: SmsRepository) : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message
    var tag: String? = null
    lateinit var id: String
    lateinit var context: Context

    fun saveTags() {

        if (tag.isNullOrEmpty()) return

        if (tag?.trim()!!.isEmpty()) return

        viewModelScope.launch {
            val newRowId = smsRepository.insertTaggedMessageId(
                AddTag(
                    tag!!,
                    id
                )
            )
            if (newRowId > -1) {
                _message.value =
                    Event("Tag Added Successfully")
            } else {
                _message.value =
                    Event("Error Occurred")
            }
        }

    }


}