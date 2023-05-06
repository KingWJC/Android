package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_conversation_entity")
public class ConversationListEntity {

    @Id(autoincrement = true)
    private Long id;
    private String conversation;//会话Id

    private String json;//会话Id
    private long seq;
    private String userId;//当前用户ID
    @Generated(hash = 686490057)
    public ConversationListEntity(Long id, String conversation, String json,
                                  long seq, String userId) {
        this.id = id;
        this.conversation = conversation;
        this.json = json;
        this.seq = seq;
        this.userId = userId;
    }
    @Generated(hash = 339783092)
    public ConversationListEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConversation() {
        return this.conversation;
    }
    public void setConversation(String conversation) {
        this.conversation = conversation;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public long getSeq() {
        return this.seq;
    }
    public void setSeq(long seq) {
        this.seq = seq;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }



  
}
