package com.elian.cms.admin.model;
// default package



/**
 * InstrumentCommentUser entity. @author MyEclipse Persistence Tools
 */

public class InstrumentCommentUser  implements java.io.Serializable {
	private static final long serialVersionUID = -176304638632469273L;
	private Integer reId;
     private InstrumentComment instrumentComment;
     private Integer userId;


    // Constructors

    /** default constructor */
    public InstrumentCommentUser() {
    }

	/** minimal constructor */
    public InstrumentCommentUser(Integer reId) {
        this.reId = reId;
    }
    
    /** full constructor */
    public InstrumentCommentUser(Integer reId, InstrumentComment instrumentComment, Integer userId) {
        this.reId = reId;
        this.instrumentComment = instrumentComment;
        this.userId = userId;
    }

   
    // Property accessors

    public Integer getReId() {
        return this.reId;
    }
    
    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public InstrumentComment getInstrumentComment() {
        return this.instrumentComment;
    }
    
    public void setInstrumentComment(InstrumentComment instrumentComment) {
        this.instrumentComment = instrumentComment;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
   








}