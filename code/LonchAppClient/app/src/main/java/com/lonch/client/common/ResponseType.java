package com.lonch.client.common;

/**
 * Created by yinazh on 18-4-16.
 * 短信注册码验证
 */

public class ResponseType {
    /**
     * 客户端逻辑错误码
     * */
    //手机号输入有误
    public static final int ERROR_SMS_MOBILE = 0x1001;
    //验证码输入有误
    public static final int ERROR_SMS_CODE = 0x1002;
    //sms_verify_failed
    public static final int ERROR_SMS_VERIFY_SEND_FAILED = 0x1003;
    //sms_verify_success
    public static final int SMS_SEND_VERIFY_SEND_SUCCESS = 0x1004;
    public static final int ERROR_REGISTER_PADDWD = 0x1005;
    public static final int REGISTER_INFO_COMPLETE = 0x1006;
    public static final int AUTO_LOGIN_SUCCESS = 0x1007;
    public static final int AUTO_LOGIN_FAILURE = 0x1008;
    public static final int CREATE_FAMILY_TOKEN_INVALID = 0x1009;

    /**
     * 服务端基础业务错误码
     * */
    //passport illegal params
    public static final int PASSPORT_ILLEGAL_PARAMS = 100000;
    //passport illegale mobile format
    public static final int PASSPORT_ILLEAGL_MOBILE_FORMAT = 100001;
    //passport mobile existed
    public static final int PASSPORT_MOBILE_EXISTED = 100002;
    //passport mobile not existed
    public static final int PASSPORT_MOBILE_NOT_EXISTED = 100003;
    //passport verify code expired
    public static final int PASSPORT_VERIFYCODE_EXPIRED = 100004;
    //passport verify code error
    public static final int PASSPORT_VERIFYCODE_ERROR = 100005;
    //passport account freeze
    public static final int PASSPORT_ACCOUNT_REEEZE = 100006;
    //passport account unfreeze
    public static final int PASSPORT_ACCOUNT_NOFREEZE = 100007;
    //passport uname or passwd error
    public static final int PASSPORT_ACCOUNT_PASSWD_ERROR = 100008;
    //passport old passwd error
    public static final int PASSPORT_ACCOUNT_OLDPASSWD_ERROR = 100009;
    //passport token invalid
    public static final int PASSPORT_TOKEN_INVALID = 100010;
    //passport token expired
    public static final int PASSPORT_TOKEN_EXPIRED = 100011;
    //wrong password format
    public static final int PASSPORT_PASSWORD_FORMAT_EXCEPTION = 100012;
    //unknown exception
    public static final int PASSPORT_UNKNOWN_EXCEPTION = 100031;
    public static final int PASSPORT_ACCOUNT_MORE_REQUESTED = 100101;
    //illegal request
    public static final int PASSPORT_ILLEGAL_REQUEST_EXCEPTION = 100103;
    //passport sms undefined
    public static final int PASSPORT_SMS_UNDEFINGED = 100104;
    //mobile not register
    public static final int PASSPORT_MOBILE_NOT_REG_EXCEPTION = 100105;
    //passport please token login or reset password
    public static final int PASSPORT_TOLENLOGIN_RESETPASSWD = 100106;

    public static final int PASSPORT_USER_NOT_IN_GROUP = 100110;
    public static final int PASSPORT_USER_NOT_IN_DEVICE_GROUP = 100109;
    //want parameters
    public static final int PASSPORT_WANT_PARAMETERS_EXCEPTION = 10003;
    //too many request from this ip
    public static final int PASSPORT_TOO_MANY_FROM_IP_EXCEPTION = 10004;
    //too many request from this mobile
    public static final int PASSPORT_TOO_MANY_FROM_MOBILE_EXCEPTION = 10005;
    //10 short messages / day
    public static final int PASSPORT_10_MESSAGE_PER_DAY_EXCEPTION = 10006;
    //illegal channel
    public static final int PASSPORT_ILLEGAL_CHANNEL_EXCEPTION = 10007;
    //signature check fail
    public static final int PASSPORT_SIGNATURE_CHECK_FAIL_EXCEPTION = 10008;
    //lost public parameters
    public static final int PASSPORT_LOST_PUB_PARAMETERS_EXCEPTION = 10009;
    //illegal request
    public static final int PASSPORT_ILLEGAL_REQUESTS_EXCEPTION = 10010;
    //not in request white list
    public static final int PASSPORT_NOT_IN_WHITE_LIST_EXCEPTION = 10011;
    //passport username existed
    public static final int PASSPORT_USERNAME_EXISTED = 100060;
    //passport uid existed
    public static final int PASSPORT_UID_EXISTED = 100061;
    //uuid illegal params
    public static final int PASSPORT_ILLEGAL_UUID = 100062;
    //sex illegal params
    public static final int PASSPORT_ILLEGAL_SEX = 100063;
    //not find user profile
    public static final int PASSPORT_NOTFIND_USER = 100064;
    //cid illegal params
    public static final int PASSPORT_ILLEGAL_CID = 100065;
    //api controller exception
    public static final int PASSPORT_CONTROLLER_EXCEPTION = 100066;
    //put data exception
    public static final int PASSPORT_PUT_DATA_EXCEPTION = 100067;
    //uuid is not administrator
    public static final int PASSPORT_NOT_GROUP_ADMIN = 100073;
    //permission denied
    public static final int PASSPORT_PERMISSION_DENIED = 100084;

    //too many request, re-try after 1 minute
    public static final int VERIFY_CODE_ERROR_MINUTE = 300011;
    //too many request, re-try after 1 hour
    public static final int VERIFY_CODE_ERROR_HOUR = 300012;


    //sms service error
    public static final int PASSPORT_PUPPY_SMS_SERVICE_ERROR = 100170;
    //need mobile and action
    public static final int PASSPORT_PUPPY_SENDSMS_NEED_PARAM_ACTION = 100171;
    //invalid sms code
    public static final int PASSPORT_PUPPY_SMSCODE_ERROR = 100172;
    //reset password fail
    public static final int PASSPORT_PUPPY_RESET_PWD_FAIL = 100173;
    //user is already exist
    public static final int PASSPORT_PUPPY_USER_EXIST = 100174;
    //user password wrong
    public static final int PASSPORT_PUPPY_USER_PWD_WRONG = 100175;
    //user is not exist
    public static final int PASSPORT_PUPPY_USER_NOT_EXIST = 100176;
    //user token Invalid
    public static final int PASSPORT_PUPPY_USER_NOT_EXIST_TOKEN_INVALID = 100177;
    //need jwttk,ip, gid and cid
    public static final int PASSPORT_PUPPY_CHECK_KID_TOKEN_NEED_PARAM_ACTION = 100178;
    //	passwd is null, need reset passwd
    public static final int PASSPORT_PUPPY_USER_PWD_NULL = 100179;
    //cid are not in same group
    public static final int PASSPORT_PUPPY_CHECK_KID_TOKEN_ERROR = 100180;
    //fail to check kid token
    public static final int PASSPORT_PUPPY_CHECK_KID_FAIL = 100181;
    //old password wrong
    public static final int PASSPORT_PUPPY_OLD_PASSWORD_WRONG = 100182;
    //fail to modify password
    public static final int PASSPORT_PUPPY_CHANGE_PWD_FAIL = 100183;
    //illegale mobile format
    public static final int PASSPORT_PUPPY_ILLEAGL_MOBILE_FORMAT = 100184;
    //send sms count limit
    /*＊
    * 发送次数上限，１分钟１次，１小时５次，１天１０次上限，
    * */
    public static final int PASSPORT_PUPPY_SMS_BIZ_LIMIT = 100185;
    //sms ram deny
    public static final int PASSPORT_PUPPY_SMS_RAM_DENY = 100186;
    //sms out of service
    public static final int PASSPORT_PUPPY_SMS_OUT_SERVICE = 100187;
    //sms unsubscript ali product
    public static final int PASSPORT_PUPPY_SMS_UNSUB = 100188;
    //sms product unsubscribe
    public static final int PASSPORT_PUPPY_SMS_PRODUCT_UNSUB = 100189;
    //sms account not exist
    public static final int PASSPORT_PUPPY_SMS_NO_ACCOUNT = 100190;
    //sms account abnormal
    public static final int PASSPORT_PUPPY_SMS_ACCOUNT_ABNORMAL = 100191;
    //illegal sms template
    public static final int PASSPORT_PUPPY_SMS_TEMPLATE_ILLEGAL = 100192;
    //illegal sms signature
    public static final int  PASSPORT_PUPPY_SMS_SIGNATURE_ILLEGAL = 100193;
    //sms invalid parameters
    public static final int PASSPORT_PUPPY_SMS_INVALID_PARAMETERSL = 100194;
    //	sms system error
    public static final int 	PASSPORT_PUPPY_SMS_SYSTEM_ERROR = 100195;
    //sms mobile count over limit
    public static final int PASSPORT_PUPPY_SMS_MOBILE_COUNT_OVER_LIMIT = 100196;
    //sms template missing parameters
    public static final int PASSPORT_PUPPY_SMS_TEMPLATE_MISSING_PARAMETERS = 100197;
    //sms invalid json param, only string
    public static final int PASSPORT_PUPPY_SMS_INVALID_JSON_PARAM = 100198;
    //sms black key control limit
    public static final int PASSPORT_PUPPY_SMS_BLACK_KEY_CONTROL_LIMIT = 100199;
    //sms param length limit
    public static final int PASSPORT_PUPPY_SMS_PARAM_LENGTH_LIMIT = 100200;
    //sms param not support url
    public static final int PASSPORT_PUPPY_SMS_PARAM_NOT_SUPPORT_URL = 100201;
    //	sms amount not enough
    public static final int PASSPORT_PUPPY_SMS_AMOUNT_NOT_ENOUGH = 100202;
    //	sms amount not enough
    public static final int DEVICE_FAMILY_DATA_NO_DATA = 20000;
    // device no bind group
    public static final int PASSPORT_DEVICE_GROUP_NO_BINDED = 100117;
    //new password is same to old password
    public static final int PASSPORT_SAME_PWD = 100119;




     /**
      * 成功码
      * */
     public static final int SUCCESS = 10000;

     /**
      * 家庭控制
      * */
     //家庭账号创建成功
    public static final int CREATE_GROUP_SUCCESS = 10000;
    public static final int CREATE_GROUP_FAILURE = 0x2001;


    /**
     * Login Token Response
     */
    public static final int LOGIN_TOKEN_RESPONSE_SUCCESS = 0x3000;
    public static final int LOGIN_TOKEN_RESPONSE_FAIL = 0x3001;


    /**
     * mainPage Group info
     * */

    public static final int GROUP_INFO_FLAG = 0x5010;
    public static final int GROUP_INFO_FLAG_BASE = 0x5012;
    public static final int GROUP_INFO_FLAG_MUMBER = 0x5013;
    public static final int USER_INFO_FLAG = 0x5020;
    public static final int USER_BASE_INFO_FLAG = 0x5021;
    public static final int USER_PERSIONAL_INFO_FLAG = 0x5021;
    public static final int DEFALUT_FLAG = -1;
    public static final int GROUP_INFO_MODIFY_SUCCESS = 0x5003;
    public static final int GROUP_INFO_MODIFY_FAILURE = 0x5004;
    public static final int GET_GROUP_INFO_MODIFY_SUCCESS = 0x5005;
    public static final int GET_GROUP_INFO_MODIFY_FAILURE = 0x5006;


    /**
     * 家庭成员界面更新标志
     * */
    public static final int START_TRANSFER_MASTER = 0x6001;
    public static final int CANCEL_TRANSFER_MASTER = 0x6002;
    public static final int END_TRANSFER_MASTER = 0x6003;
    public static final int TRANSFER_MASTER_FAILURE = 0x6009;
    public static final int DELET_MUMBER_FROM_GROUP = 0x6004;
    public static final int DELET_MUMBER_FROM_GROUP_FAILURE = 0x6008;
    public static final int TRANSFER_TO_MUMBER_SELECTED = 0x6005;
    public static final int TRANSFER_TO_MUMBER_SELECTING = 0x6006;
    public static final int EXIT_GROUP_SUCCESS = 0x6007;
    public static final int EXIT_GROUP_FAILURE = 0x6010;
    public static final int GET_GROUP_MUMBERS_SUCCESS = 0x6011;
    public static final int GET_GROUP_MUMBERS_FAILURE = 0x6012;





    /**
     * 添加家庭成员
     * */
    public static final int ADD_MUMBER_SUCCESS = 0x7001;
    public static final int ADD_MUMBER_FAILURE = 0x7002;
    //user already in group
    public static final int PASSPORT_USER_IN_GROUP = 100083;
    //user not exist
    public static final int PASSPORT_USER_NOT_EXIST = 100079;

    /**
     * 解绑设备
     * */
    public static final int UNTIE_DEVICE_SUCCESS = 0x8001;
    public static final int UNTIE_DEVICE_FAILURE = 0x8002;


    /**
     *  Reset pwd
     */
    public static final int PASSPORT_VERIFY_CODE_EXPIRED=100004;
    public static final int PASSPORT_VERIFY_CODE_ERROR=100005;
}
