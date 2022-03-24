package com.example.map.ServicesData;

import java.util.List;

public class ServicesDataClass {
    String status,_id,title,name,description,createdAt,updatedAt,contactInfo,emailId;
Images[] images;
List<Categories> categories;

    public ServicesDataClass(String status, String _id, String title, String name, String description, String createdAt, String updatedAt, String contactInfo, String emailId, Images[] images, List<Categories> categories) {
        this.status = status;
        this._id = _id;
        this.title = title;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.contactInfo = contactInfo;
        this.emailId = emailId;
        this.images = images;
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Images[] getImages() {
        return images;
    }

    public void setImages(Images[] images) {
        this.images = images;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}
