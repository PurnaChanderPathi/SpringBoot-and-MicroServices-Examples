package com.purna.entity;

public class AttachmentData {

    private byte[] attachment;
    private String attachmentType;

    public AttachmentData(byte[] attachment, String attachmentType) {
        this.attachment = attachment;
        this.attachmentType = attachmentType;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}
