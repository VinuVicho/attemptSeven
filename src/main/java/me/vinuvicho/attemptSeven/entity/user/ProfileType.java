package me.vinuvicho.attemptSeven.entity.user;

public enum ProfileType {       //TODO: structure
    PUBLIC,
    PROTECTED,          //no messages allowed, only friends
    ONLY_SUBSCRIBERS,   //protected + only subs can see posts
    FRIENDS             //only friends can send messages + only_subscribed
}
