package com.imdzz.blog.enums;

/**
 * 博客的分类
 */
public enum ClassificationEnum {
    LINUX("Linux"),
    MYSQL("Mysql"),
    JAVA("Java"),
    SPRING_BOOT("Spring boot");

    private String classification;

    ClassificationEnum(String classification){
        this.classification = classification;
    }

    public String getClassification(){
        return this.classification;
    }
}
