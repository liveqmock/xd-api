package com.xindong.api.domain.vo;

import java.util.Date;

import com.xindong.api.domain.Comment;


public class CommentVO {
	
	/** 评价ID */
	private Integer id;
	
	/** 属性ID */
	private Integer skuId;
	
	/** 商品ID */
	private Integer itemId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 评价标题 */
	private String title;
	
	/** 评价内容 */
	private String content;
	
	/** 评价分数 */
	private Integer score;
	private Date commentTime;//评价时间
	private String bakFirst;
	private String userName;
	private String imgOne;//评论图片1
	private String imgTwo;//评论图片2
    private String imgThree;//评论图片3
	private String imgFour;//评论图片4
	public CommentVO() {}
	public CommentVO(Comment comment) {
		this.content =comment.getContent();
		this.id=comment.getId();
		this.itemId =comment.getItemId();
		this.score=comment.getScore();
		this.skuId =comment.getSkuId();
		this.title =comment.getTitle();
		this.bakFirst =comment.getBakFirst();
		this.commentTime =comment.getCommentTime();
		this.userName =comment.getUserName();
		this.imgFour =comment.getImgFour();
		this.imgOne =comment.getImgOne();
		this.imgThree =comment.getImgThree();
		this.imgTwo =comment.getImgTwo();
	}
	
	public String getImgOne() {
		return imgOne;
	}
	public void setImgOne(String imgOne) {
		this.imgOne = imgOne;
	}
	public String getImgTwo() {
		return imgTwo;
	}
	public void setImgTwo(String imgTwo) {
		this.imgTwo = imgTwo;
	}
	public String getImgThree() {
		return imgThree;
	}
	public void setImgThree(String imgThree) {
		this.imgThree = imgThree;
	}
	public String getImgFour() {
		return imgFour;
	}
	public void setImgFour(String imgFour) {
		this.imgFour = imgFour;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public String getBakFirst() {
		return bakFirst;
	}
	public void setBakFirst(String bakFirst) {
		this.bakFirst = bakFirst;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	
	
}
