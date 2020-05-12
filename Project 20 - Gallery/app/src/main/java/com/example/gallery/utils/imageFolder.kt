package com.example.gallery.utils

class imageFolder {
    private var path: String? = null
    private var FolderName: String? = null
    private var numberOfPics = 0
    private var firstPic: String? = null

    fun imageFolder() {}

    fun imageFolder(path: String?, folderName: String?) {
        this.path = path
        FolderName = folderName
    }

    fun getPath(): String? {
        return path
    }

    fun setPath(path: String?) {
        this.path = path
    }

    fun getFolderName(): String? {
        return FolderName
    }

    fun setFolderName(folderName: String?) {
        FolderName = folderName
    }

    fun getNumberOfPics(): Int {
        return numberOfPics
    }

    fun setNumberOfPics(numberOfPics: Int) {
        this.numberOfPics = numberOfPics
    }

    fun addpics() {
        numberOfPics++
    }

    fun getFirstPic(): String? {
        return firstPic
    }

    fun setFirstPic(firstPic: String?) {
        this.firstPic = firstPic
    }
}