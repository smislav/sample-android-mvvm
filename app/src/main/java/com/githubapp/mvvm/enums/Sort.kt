package com.githubapp.mvvm.enums

enum class Sort(val id: Int, val value: String) {
    STARS(0, "stars"),
    FORKS(1, "forks"),
    UPDATED(2, "updated");

    companion object {
        fun findByID(id: Int): Sort?{
            if(id == FORKS.id){
                return FORKS
            }
            if(id == STARS.id){
                return STARS
            }
            if(id == UPDATED.id){
                return UPDATED
            }
            return null
        }
    }
}