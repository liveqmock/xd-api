package com.xindong.api.domain.query;

import java.io.Serializable;
/**筛选查询
 * @author lichaoxiong
 *
 */
public class SearchQuery extends BaseSearchForMysqlVo  implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer heartScore;//心动指数
    private Integer qualityScore;//品质指数
    private Integer challengeScore;//挑战指数
    /** 升级序，1心动指数升序，2心动指数降序，3品质指数升序，4品质指数降序，5挑战指数升序 6挑战指数降序 */
    private String sort;
    private Integer destinationId1;//商品一级目的地

    private Integer destinationId2;//商品二级目的地

    private Integer subjectId1;//商品一级主题

    private Integer subjectId2;//商品二级主题

    private Integer dateId1;//商品二级时间

    private Integer dateId2;//商品二级时间

    private Integer originId1;//商品二级出发地

    private Integer originId2;//商品二级出发地
    private String n;//关键字查询
    
   	public String getN() {
   		return n;
   	}
   	public void setN(String n) {
   		this.n = n;
   	}
	public Integer getDestinationId1() {
		return destinationId1 == null ? 0 :destinationId1;
	}
	public void setDestinationId1(Integer destinationId1) {
		this.destinationId1 = destinationId1;
	}
	public Integer getDestinationId2() {
		return destinationId2== null ? 0 :destinationId2;
	}
	public void setDestinationId2(Integer destinationId2) {
		this.destinationId2 = destinationId2;
	}
	public Integer getSubjectId1() {
		return subjectId1== null ? 0 :subjectId1;
	}
	public void setSubjectId1(Integer subjectId1) {
		this.subjectId1 = subjectId1;
	}
	public Integer getSubjectId2() {
		return subjectId2== null ? 0 :subjectId2;
	}
	public void setSubjectId2(Integer subjectId2) {
		this.subjectId2 = subjectId2;
	}
	public Integer getDateId1() {
		return dateId1== null ? 0 :dateId1;
	}
	public void setDateId1(Integer dateId1) {
		this.dateId1 = dateId1;
	}
	public Integer getDateId2() {
		return dateId2== null ? 0 :dateId2;
	}
	public void setDateId2(Integer dateId2) {
		this.dateId2 = dateId2;
	}
	public Integer getOriginId1() {
		return originId1== null ? 0 :originId1;
	}
	public void setOriginId1(Integer originId1) {
		this.originId1 = originId1;
	}
	public Integer getOriginId2() {
		return originId2== null ? 0 :originId2;
	}
	public void setOriginId2(Integer originId2) {
		this.originId2 = originId2;
	}
	public Integer getHeartScore() {
		return heartScore== null ? 0 :heartScore;
	}
	public void setHeartScore(Integer heartScore) {
		this.heartScore = heartScore;
	}
	public Integer getQualityScore() {
		return qualityScore== null ? 0 :qualityScore;
	}
	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
	}
	public Integer getChallengeScore() {
		return challengeScore== null ? 0 :challengeScore;
	}
	public void setChallengeScore(Integer challengeScore) {
		this.challengeScore = challengeScore;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
    
   
}