package com.example.noteapplicaton.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapplicaton.api.NotesAPI
import com.example.noteapplicaton.models.NoteRequest
import com.example.noteapplicaton.models.NoteResponse
import com.example.noteapplicaton.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val notesAPI: NotesAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
        get() = _statusLiveData



    suspend fun getNote(){
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Sucess(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }

    }
    //TO CREATE NOTE FOR NETWORK REQUEST
    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponse(response,"Note Created")
    }

    suspend fun deletNote(noteId:String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleResponse(response,"Note Deleted")

    }
    suspend fun updateNote(noteId: String,noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response =notesAPI.updateNote(noteId,noteRequest)
        handleResponse(response,"Note Updated")

    }
    private fun handleResponse(response: Response<NoteResponse>,message:String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Sucess(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error(message))
        }
    }
}