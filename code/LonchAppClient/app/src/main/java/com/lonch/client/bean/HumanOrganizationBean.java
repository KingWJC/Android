package com.lonch.client.bean;

import java.util.List;

public class HumanOrganizationBean {


    /**
     * opFlag : true
     * error :
     * serviceResult : {"caid":"c39f94ef1f24491ba3bf62b3f4ad5684","source":"0005","comments":null,"deleted":false,"createtime":1610164187000,"modifiedtime":1610201740000,"name":"白二鹏","anotherName":null,"employeeCode":null,"position":null,"parentid":null,"isLeader":0,"positionNature":null,"authenticationStatus":"0","confirmStatus":"0","userStatus":"0","telephone":null,"cellphone":"15711494546","identificationtypecode":null,"identificationnumber":"410911199510190000","identificationStartTime":null,"identificationEndTime":null,"email":null,"personnelType":null,"remark":null,"portrait":"https://wework.qpic.cn/wwhead/nMl9ssowtibVGyrmvBiaibzDjw4gTJ4ds7ve2ZqVFfWYVZu3VPw1jMITiaFDqm1Micjv7NnqUeDpriagU/0","avatarmediaid":null,"thumbPortrait":null,"organizenums":null,"departmentid":null,"gendercode":"1","birthday":null,"ethnicitycode":null,"qq":null,"wechat":null,"openid":null,"address":null,"diplomacode":null,"degreecode":null,"degreetypecode":null,"graduationcollege":null,"graduationmajor":null,"graduationyear":null,"englishlevelcode":null,"computerlevelcode":null,"auditStatus":0,"createHumanId":null,"adress":null,"dataOwnerOrgId":"129179145690288128","dataOwnerOrgList":[{"caid":"129179145690288128","source":null,"showorder":null,"comments":null,"deleted":null,"createtime":null,"modifiedtime":null,"parentid":null,"name":"朗致集团","provincecode":null,"citycode":null,"districtcode":null,"address":null,"postnumber":null,"website":null,"organizetypecode":null,"organizenumber":null,"linkmanname":null,"invoicerise":null,"dutyparagraph":null,"linkmanaddress":null,"linkmangendercode":null,"linkmandepartment":null,"linkmantel":null,"linkmanphone":null,"linkmanemail":null,"logo":null,"isTopOrg":null,"dataOwnerOrgId":null}]}
     * timestamp : 2021-02-20 15:20:37
     */

    private boolean opFlag;
    private String error;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ServiceResultBean {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * caid : c39f94ef1f24491ba3bf62b3f4ad5684
         * source : 0005
         * comments : null
         * deleted : false
         * createtime : 1610164187000
         * modifiedtime : 1610201740000
         * name : 白二鹏
         * anotherName : null
         * employeeCode : null
         * position : null
         * parentid : null
         * isLeader : 0
         * positionNature : null
         * authenticationStatus : 0
         * confirmStatus : 0
         * userStatus : 0
         * telephone : null
         * cellphone : 15711494546
         * identificationtypecode : null
         * identificationnumber : 410911199510190000
         * identificationStartTime : null
         * identificationEndTime : null
         * email : null
         * personnelType : null
         * remark : null
         * portrait : https://wework.qpic.cn/wwhead/nMl9ssowtibVGyrmvBiaibzDjw4gTJ4ds7ve2ZqVFfWYVZu3VPw1jMITiaFDqm1Micjv7NnqUeDpriagU/0
         * avatarmediaid : null
         * thumbPortrait : null
         * organizenums : null
         * departmentid : null
         * gendercode : 1
         * birthday : null
         * ethnicitycode : null
         * qq : null
         * wechat : null
         * openid : null
         * address : null
         * diplomacode : null
         * degreecode : null
         * degreetypecode : null
         * graduationcollege : null
         * graduationmajor : null
         * graduationyear : null
         * englishlevelcode : null
         * computerlevelcode : null
         * auditStatus : 0
         * createHumanId : null
         * adress : null
         * dataOwnerOrgId : 129179145690288128
         * dataOwnerOrgList : [{"caid":"129179145690288128","source":null,"showorder":null,"comments":null,"deleted":null,"createtime":null,"modifiedtime":null,"parentid":null,"name":"朗致集团","provincecode":null,"citycode":null,"districtcode":null,"address":null,"postnumber":null,"website":null,"organizetypecode":null,"organizenumber":null,"linkmanname":null,"invoicerise":null,"dutyparagraph":null,"linkmanaddress":null,"linkmangendercode":null,"linkmandepartment":null,"linkmantel":null,"linkmanphone":null,"linkmanemail":null,"logo":null,"isTopOrg":null,"dataOwnerOrgId":null}]
         */
        private String id;
        private String caid;
        private String source;
        private Object comments;
        private boolean deleted;
        private long createtime;
        private long modifiedtime;
        private String name;
        private Object anotherName;
        private Object employeeCode;
        private Object position;
        private Object parentid;
        private int isLeader;
        private Object positionNature;
        private String authenticationStatus;
        private String confirmStatus;
        private String userStatus;
        private Object telephone;
        private String cellphone;
        private Object identificationtypecode;
        private String identificationnumber;
        private Object identificationStartTime;
        private Object identificationEndTime;
        private Object email;
        private Object personnelType;
        private Object remark;
        private String portrait;
        private Object avatarmediaid;
        private Object thumbPortrait;
        private Object organizenums;
        private Object departmentid;
        private String gendercode;
        private Object birthday;
        private Object ethnicitycode;
        private Object qq;
        private Object wechat;
        private Object openid;
        private Object address;
        private Object diplomacode;
        private Object degreecode;
        private Object degreetypecode;
        private Object graduationcollege;
        private Object graduationmajor;
        private Object graduationyear;
        private Object englishlevelcode;
        private Object computerlevelcode;
        private int auditStatus;
        private Object createHumanId;
        private Object adress;
        private String dataOwnerOrgId;
        private List<DataOwnerOrgListBean> dataOwnerOrgList;

        public String getCaid() {
            return caid;
        }

        public void setCaid(String caid) {
            this.caid = caid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getModifiedtime() {
            return modifiedtime;
        }

        public void setModifiedtime(long modifiedtime) {
            this.modifiedtime = modifiedtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAnotherName() {
            return anotherName;
        }

        public void setAnotherName(Object anotherName) {
            this.anotherName = anotherName;
        }

        public Object getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(Object employeeCode) {
            this.employeeCode = employeeCode;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
        }

        public Object getParentid() {
            return parentid;
        }

        public void setParentid(Object parentid) {
            this.parentid = parentid;
        }

        public int getIsLeader() {
            return isLeader;
        }

        public void setIsLeader(int isLeader) {
            this.isLeader = isLeader;
        }

        public Object getPositionNature() {
            return positionNature;
        }

        public void setPositionNature(Object positionNature) {
            this.positionNature = positionNature;
        }

        public String getAuthenticationStatus() {
            return authenticationStatus;
        }

        public void setAuthenticationStatus(String authenticationStatus) {
            this.authenticationStatus = authenticationStatus;
        }

        public String getConfirmStatus() {
            return confirmStatus;
        }

        public void setConfirmStatus(String confirmStatus) {
            this.confirmStatus = confirmStatus;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public Object getIdentificationtypecode() {
            return identificationtypecode;
        }

        public void setIdentificationtypecode(Object identificationtypecode) {
            this.identificationtypecode = identificationtypecode;
        }

        public String getIdentificationnumber() {
            return identificationnumber;
        }

        public void setIdentificationnumber(String identificationnumber) {
            this.identificationnumber = identificationnumber;
        }

        public Object getIdentificationStartTime() {
            return identificationStartTime;
        }

        public void setIdentificationStartTime(Object identificationStartTime) {
            this.identificationStartTime = identificationStartTime;
        }

        public Object getIdentificationEndTime() {
            return identificationEndTime;
        }

        public void setIdentificationEndTime(Object identificationEndTime) {
            this.identificationEndTime = identificationEndTime;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getPersonnelType() {
            return personnelType;
        }

        public void setPersonnelType(Object personnelType) {
            this.personnelType = personnelType;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public Object getAvatarmediaid() {
            return avatarmediaid;
        }

        public void setAvatarmediaid(Object avatarmediaid) {
            this.avatarmediaid = avatarmediaid;
        }

        public Object getThumbPortrait() {
            return thumbPortrait;
        }

        public void setThumbPortrait(Object thumbPortrait) {
            this.thumbPortrait = thumbPortrait;
        }

        public Object getOrganizenums() {
            return organizenums;
        }

        public void setOrganizenums(Object organizenums) {
            this.organizenums = organizenums;
        }

        public Object getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(Object departmentid) {
            this.departmentid = departmentid;
        }

        public String getGendercode() {
            return gendercode;
        }

        public void setGendercode(String gendercode) {
            this.gendercode = gendercode;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getEthnicitycode() {
            return ethnicitycode;
        }

        public void setEthnicitycode(Object ethnicitycode) {
            this.ethnicitycode = ethnicitycode;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getWechat() {
            return wechat;
        }

        public void setWechat(Object wechat) {
            this.wechat = wechat;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getDiplomacode() {
            return diplomacode;
        }

        public void setDiplomacode(Object diplomacode) {
            this.diplomacode = diplomacode;
        }

        public Object getDegreecode() {
            return degreecode;
        }

        public void setDegreecode(Object degreecode) {
            this.degreecode = degreecode;
        }

        public Object getDegreetypecode() {
            return degreetypecode;
        }

        public void setDegreetypecode(Object degreetypecode) {
            this.degreetypecode = degreetypecode;
        }

        public Object getGraduationcollege() {
            return graduationcollege;
        }

        public void setGraduationcollege(Object graduationcollege) {
            this.graduationcollege = graduationcollege;
        }

        public Object getGraduationmajor() {
            return graduationmajor;
        }

        public void setGraduationmajor(Object graduationmajor) {
            this.graduationmajor = graduationmajor;
        }

        public Object getGraduationyear() {
            return graduationyear;
        }

        public void setGraduationyear(Object graduationyear) {
            this.graduationyear = graduationyear;
        }

        public Object getEnglishlevelcode() {
            return englishlevelcode;
        }

        public void setEnglishlevelcode(Object englishlevelcode) {
            this.englishlevelcode = englishlevelcode;
        }

        public Object getComputerlevelcode() {
            return computerlevelcode;
        }

        public void setComputerlevelcode(Object computerlevelcode) {
            this.computerlevelcode = computerlevelcode;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public Object getCreateHumanId() {
            return createHumanId;
        }

        public void setCreateHumanId(Object createHumanId) {
            this.createHumanId = createHumanId;
        }

        public Object getAdress() {
            return adress;
        }

        public void setAdress(Object adress) {
            this.adress = adress;
        }

        public String getDataOwnerOrgId() {
            return dataOwnerOrgId;
        }

        public void setDataOwnerOrgId(String dataOwnerOrgId) {
            this.dataOwnerOrgId = dataOwnerOrgId;
        }

        public List<DataOwnerOrgListBean> getDataOwnerOrgList() {
            return dataOwnerOrgList;
        }

        public void setDataOwnerOrgList(List<DataOwnerOrgListBean> dataOwnerOrgList) {
            this.dataOwnerOrgList = dataOwnerOrgList;
        }

        public static class DataOwnerOrgListBean {
            /**
             * caid : 129179145690288128
             * source : null
             * showorder : null
             * comments : null
             * deleted : null
             * createtime : null
             * modifiedtime : null
             * parentid : null
             * name : 朗致集团
             * provincecode : null
             * citycode : null
             * districtcode : null
             * address : null
             * postnumber : null
             * website : null
             * organizetypecode : null
             * organizenumber : null
             * linkmanname : null
             * invoicerise : null
             * dutyparagraph : null
             * linkmanaddress : null
             * linkmangendercode : null
             * linkmandepartment : null
             * linkmantel : null
             * linkmanphone : null
             * linkmanemail : null
             * logo : null
             * isTopOrg : null
             * dataOwnerOrgId : null
             */

            private String caid;
            private Object source;
            private Object showorder;
            private Object comments;
            private Object deleted;
            private Object createtime;
            private Object modifiedtime;
            private Object parentid;
            private String name;
            private Object provincecode;
            private Object citycode;
            private Object districtcode;
            private Object address;
            private Object postnumber;
            private Object website;
            private Object organizetypecode;
            private Object organizenumber;
            private Object linkmanname;
            private Object invoicerise;
            private Object dutyparagraph;
            private Object linkmanaddress;
            private Object linkmangendercode;
            private Object linkmandepartment;
            private Object linkmantel;
            private Object linkmanphone;
            private Object linkmanemail;
            private Object logo;
            private Object isTopOrg;
            private Object dataOwnerOrgId;

            public String getCaid() {
                return caid;
            }

            public void setCaid(String caid) {
                this.caid = caid;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getShoworder() {
                return showorder;
            }

            public void setShoworder(Object showorder) {
                this.showorder = showorder;
            }

            public Object getComments() {
                return comments;
            }

            public void setComments(Object comments) {
                this.comments = comments;
            }

            public Object getDeleted() {
                return deleted;
            }

            public void setDeleted(Object deleted) {
                this.deleted = deleted;
            }

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getModifiedtime() {
                return modifiedtime;
            }

            public void setModifiedtime(Object modifiedtime) {
                this.modifiedtime = modifiedtime;
            }

            public Object getParentid() {
                return parentid;
            }

            public void setParentid(Object parentid) {
                this.parentid = parentid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getProvincecode() {
                return provincecode;
            }

            public void setProvincecode(Object provincecode) {
                this.provincecode = provincecode;
            }

            public Object getCitycode() {
                return citycode;
            }

            public void setCitycode(Object citycode) {
                this.citycode = citycode;
            }

            public Object getDistrictcode() {
                return districtcode;
            }

            public void setDistrictcode(Object districtcode) {
                this.districtcode = districtcode;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getPostnumber() {
                return postnumber;
            }

            public void setPostnumber(Object postnumber) {
                this.postnumber = postnumber;
            }

            public Object getWebsite() {
                return website;
            }

            public void setWebsite(Object website) {
                this.website = website;
            }

            public Object getOrganizetypecode() {
                return organizetypecode;
            }

            public void setOrganizetypecode(Object organizetypecode) {
                this.organizetypecode = organizetypecode;
            }

            public Object getOrganizenumber() {
                return organizenumber;
            }

            public void setOrganizenumber(Object organizenumber) {
                this.organizenumber = organizenumber;
            }

            public Object getLinkmanname() {
                return linkmanname;
            }

            public void setLinkmanname(Object linkmanname) {
                this.linkmanname = linkmanname;
            }

            public Object getInvoicerise() {
                return invoicerise;
            }

            public void setInvoicerise(Object invoicerise) {
                this.invoicerise = invoicerise;
            }

            public Object getDutyparagraph() {
                return dutyparagraph;
            }

            public void setDutyparagraph(Object dutyparagraph) {
                this.dutyparagraph = dutyparagraph;
            }

            public Object getLinkmanaddress() {
                return linkmanaddress;
            }

            public void setLinkmanaddress(Object linkmanaddress) {
                this.linkmanaddress = linkmanaddress;
            }

            public Object getLinkmangendercode() {
                return linkmangendercode;
            }

            public void setLinkmangendercode(Object linkmangendercode) {
                this.linkmangendercode = linkmangendercode;
            }

            public Object getLinkmandepartment() {
                return linkmandepartment;
            }

            public void setLinkmandepartment(Object linkmandepartment) {
                this.linkmandepartment = linkmandepartment;
            }

            public Object getLinkmantel() {
                return linkmantel;
            }

            public void setLinkmantel(Object linkmantel) {
                this.linkmantel = linkmantel;
            }

            public Object getLinkmanphone() {
                return linkmanphone;
            }

            public void setLinkmanphone(Object linkmanphone) {
                this.linkmanphone = linkmanphone;
            }

            public Object getLinkmanemail() {
                return linkmanemail;
            }

            public void setLinkmanemail(Object linkmanemail) {
                this.linkmanemail = linkmanemail;
            }

            public Object getLogo() {
                return logo;
            }

            public void setLogo(Object logo) {
                this.logo = logo;
            }

            public Object getIsTopOrg() {
                return isTopOrg;
            }

            public void setIsTopOrg(Object isTopOrg) {
                this.isTopOrg = isTopOrg;
            }

            public Object getDataOwnerOrgId() {
                return dataOwnerOrgId;
            }

            public void setDataOwnerOrgId(Object dataOwnerOrgId) {
                this.dataOwnerOrgId = dataOwnerOrgId;
            }
        }
    }
}
