package com.suddenly.auth;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TokenPayload implements Serializable {

    // (issuer)     发行者
    private String iss;

    // (subject)    主题
    private String sub;

    // (audience)   受众
    private String aud;

    // (expiration time)    到期时间
    private Date exp;

    // (Not Before)     有效起始日期
    private Date nbf;

    // (Issued At)      签发
    private Date iat;

    // (JWT ID)     JWT ID
    private String jti;

    public TokenPayload() {
        this.iss = "suddenly";
        this.sub = "";
        this.aud = "";
        Calendar expDate = Calendar.getInstance();
        expDate.set(Calendar.YEAR, 2099);
        expDate.set(Calendar.MONTH, 1);
        expDate.set(Calendar.DATE, 1);
        this.exp = expDate.getTime();
        this.nbf = new Date();
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public Date getNbf() {
        return nbf;
    }

    public void setNbf(Date nbf) {
        this.nbf = nbf;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
