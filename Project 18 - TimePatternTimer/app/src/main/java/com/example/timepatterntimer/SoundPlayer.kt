package com.example.timepatterntimer

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import java.util.HashMap
class SoundPlayer {
    val BBIBBI = R.raw.bbibbi

    private lateinit var soundPool: SoundPool
    private lateinit var soundPoolMap: HashMap<Int, Int>

    fun initSounds(context:Context){
        var attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        soundPool = SoundPool.Builder().setAudioAttributes(attributes).build()
        soundPoolMap = HashMap(2)
        soundPoolMap.put(BBIBBI, soundPool.load(context,BBIBBI, 1))
    }

    fun play(raw_id : Int){
        if (soundPoolMap.containsKey(raw_id)){
            soundPool.play(soundPoolMap.get(raw_id)!!, 1f,1f,1,0,1f)
        }
    }

}