package com.lonch.client.bean;

public class BaseSixFourBean {


    /**
     * dataOwnerOrgId : 1ee05711d7ac44e0aeea621ad4de8270
     * accountId : 564f28035ec64e59bf6900144505ef28
     * exp : 1608753486
     * userId : f51d19c4a7e046ca8671b9431f24f440
     * iat : 1608537486
     * username : 15711494546
     */

    private String dataOwnerOrgId;
    private String accountId;
    private int exp;
    private String userId;
    private int iat;
    private String username;

    public String getDataOwnerOrgId() {
        return dataOwnerOrgId;
    }

    public void setDataOwnerOrgId(String dataOwnerOrgId) {
        this.dataOwnerOrgId = dataOwnerOrgId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIat() {
        return iat;
    }

    public void setIat(int iat) {
        this.iat = iat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
