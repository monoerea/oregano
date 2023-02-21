package com.mono.oregano.data.model;

import com.mono.oregano.domain.entity.Users.UserEntity;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;

public class PostsTest extends TestCase {

    public void testToDocument() {
        UserEntity user = new UserEntity();
        Posts posts = new Posts("1212312","A title","my content",user,new Date(),"some image");
        HashMap<String, Object>object = posts.toDocument();
        System.out.println(object.get("id"));
    }
}