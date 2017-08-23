package com.elian.cms.admin.model;
// default package



/**
 * MedicineCommentUser entity. @author MyEclipse Persistence Tools
 */

public class MedicineCommentUser  implements java.io.Serializable {
	private static final long serialVersionUID = 4053051972262264215L;
	private Integer reId;
     private MedicineComment medicineComment;
     private Integer userId;


    // Constructors

    /** default constructor */
    public MedicineCommentUser() {
    }

	/** minimal constructor */
    public MedicineCommentUser(Integer reId) {
        this.reId = reId;
    }
    
    /** full constructor */
    public MedicineCommentUser(Integer reId, MedicineComment medicineComment, Integer userId) {
        this.reId = reId;
        this.medicineComment = medicineComment;
        this.userId = userId;
    }

   
    // Property accessors

    public Integer getReId() {
        return this.reId;
    }
    
    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public MedicineComment getMedicineComment() {
        return this.medicineComment;
    }
    
    public void setMedicineComment(MedicineComment medicineComment) {
        this.medicineComment = medicineComment;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
   








}