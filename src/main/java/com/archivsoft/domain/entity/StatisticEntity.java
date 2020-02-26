/**
 * ========================================================
 *
 * @FileName : StatisticEntity.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.domain.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 클래스명 : StatisticEntity
 * <BR>
 * <PRE>.
 * statistic 테이블과 직접 관련되며 매출, 매입 통계의 모든 속성 정보 및 생성자 작성 메소드를 가지고 있습니다.
 * </PRE>
 * <BR>
 * <p>Copyright(c) 2020 by National Archives & Records Service
 * All Rights reserved. </p>
 *
 * @author 홍성관
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "statistic")
public class StatisticEntity {

    /**
     * 고유 키값 승인번호
     */
    @Id
    @Column(length = 30, nullable = true)
    private String approveNum;

    /**
     * 작성일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate regDt;

    /**
     * 발급일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate issueDt;

    /**
     * 전송일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate sendDt;

    /**
     * 공급자사업자등록번호
     */
    @Column(length = 20, nullable = true)
    private String businessNum;

    /**
     * 종사업장번호
     */
    @Column(length = 20, nullable = true)
    private String subBusinessNum;

    /**
     * 상호
     */
    @Column(length = 50, nullable = false)
    private String companyNM;

    /**
     * 대표자명
     */
    @Column(length = 15, nullable = true)
    private String ceoName;

    /**
     * 주소
     */
    @Column(length = 100, nullable = true)
    private String address;

    /**
     * 공급받는자사업자등록번호
     */
    @Column(length = 20, nullable = true)
    private String recipientBusinessNum;

    /**
     * 종사업장번호
     */
    @Column(length = 20, nullable = true)
    private String recipientSubBusinessNum;

    /**
     * 상호
     */
    @Column(length = 50, nullable = true)
    private String recipientCompanyNM;

    /**
     * 대표자명
     */
    @Column(length = 15, nullable = true)
    private String recipientCeoNM;

    /**
     * 주소
     */
    @Column(length = 100, nullable = true)
    private String recipientAddress;

    /**
     * 합계금액  공급가액 + 세액
     */
    @Column(nullable = true)
    private int totalPrice;

    /**
     * 공급가액 = 공급 단가
     */
    @Column(nullable = true)
    private int supplyPrice;

    /**
     * 세액 = 공급단가 의 0.1% ( 10% ) 단, 소수점은 반올림
     */
    @Column(nullable = true)
    private int taxAmount;

    /**
     * 전자세금계산서분류 세급계산서/수정세금계산서
     */
    @Column(length = 25, nullable = true)
    private String invoiceClassify;

    /**
     * 전자세금계산서종류 일반, 일반(수정)
     */
    @Column(length = 10, nullable = true)
    private String invoiceType;

    /**
     * 발급유형 인터넷발급/직접발급/ASP발급/겸용서식발급
     */
    @Column(length = 20, nullable = true)
    private String issueType;

    /**
     * 비고
     */
    @Column(length = 125, nullable = true)
    private String priceNote;

    /**
     * 영수/청구 구분
     */
    @Column(length = 10, nullable = true)
    private String classfication;

    /**
     * 공급자 이메일
     */
    @Column(length = 50, nullable = true)
    private String email;

    /**
     * 공급받는자 이메일1
     */
    @Column(length = 50, nullable = true)
    private String recipientEmail1;

    /**
     * 공급받는자 이메일2
     */
    @Column(length = 50, nullable = true)
    private String recipientEmail2;

    /**
     * 품목일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate itemDT;

    /**
     * 품목명
     */
    @Column(length = 150, nullable = true)
    private String itemNM;

    /**
     * 품목규격
     */
    @Column(length = 25, nullable = true)
    private String itemSpec;

    /**
     * 품목수량
     */
    @Column(length = 25, nullable = true)
    private String itemQuantity;

    /**
     * 품목단가
     */
    @Column(nullable = true)
    private int itemUnitPrice;

    /**
     * 품목공급가액
     */
    @Column(nullable = true)
    private int itemSupplyPrice;

    /**
     * 품목세액
     */
    @Column(nullable = true)
    private int itemTaxPrice;

    /**
     * 품목비고
     */
    @Column(length = 30, nullable = true)
    private String itemNote;

    /**
     * 수금여부
     */
    @Column(length = 20, nullable = true)
    private String collectionMoneyYN;

    /**
     * 수금일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate collectionMoneyDt;

    /**
     * 지급여부
     */
    @Column(length = 20, nullable = true)
    private String paymentYN;

    /**
     * 지급일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = true)
    private LocalDate paymentDt;

    /**
     * 비고
     */
    @Column(length = 150, nullable = true)
    private String note;

    /**
     * 매출/매입 구분
     * 공급자 사업번호가 862-86-00042 일경우 매출 아닐경우 매입 ( 매출 S/ 매입 P)
     */
    @Column(length = 1, nullable = true)
    private String salePurchaseClassification;

    /**
     * 월별 매출 건수 합계
     */
    @Transient
    private int SalesCount;

    /**
     * 월별 매출 공급가액 합계
     */
    @Transient
    private int SalesSupplyPrice;

    /**
     * 월별 매출 세액 합계
     */
    @Transient
    private int SalesTaxAmount;

    /**
     * 월별 매출액 총합
     */
    @Transient
    private int SalesTotalPrice;

    /**
     * 월별 매입 건수 합계
     */
    @Transient
    private int PurchasesCount;

    /**
     * 월별 매입 공급가액 합계
     */
    @Transient
    private int PurchasesSupplyPrice;

    /**
     * 월별 매입 세액 합계
     */
    @Transient
    private int PurchasesTaxAmount;

    /**
     * 월별 매입액 총합
     */
    @Transient
    private int PurchasesTotalPrice;

    @Transient
    private int salePurchaseMoney;

    /**
     * StatisticEntity 생성자
     */
    @Builder
    public StatisticEntity(LocalDate regDt, String approveNum, LocalDate issueDt, LocalDate sendDt, String businessNum, String subBusinessNum
            , String companyNM, String ceoName, String address, String recipientBusinessNum
            , String recipientSubBusinessNum, String recipientCompanyNM, String recipientCeoNM, String recipientAddress
            , int totalPrice, int supplyPrice, int taxAmount, String invoiceClassify, String invoiceType
            , String issueType, String priceNote, String classfication, String email
            , String recipientEmail1, String recipientEmail2, LocalDate itemDT, String itemNM
            , String itemSpec, String itemQuantity, int itemUnitPrice, int itemSupplyPrice
            , int itemTaxPrice, String itemNote, String collectionMoneyYN, LocalDate collectionMoneyDt
            , String paymentYN, LocalDate paymentDt, String note, String salePurchaseClassification) {
        this.regDt = regDt;
        this.approveNum = approveNum;
        this.issueDt = issueDt;
        this.sendDt = sendDt;
        this.businessNum = businessNum;
        this.subBusinessNum = subBusinessNum;
        this.companyNM = companyNM;
        this.ceoName = ceoName;
        this.address = address;
        this.recipientBusinessNum = recipientBusinessNum;
        this.recipientSubBusinessNum = recipientSubBusinessNum;
        this.recipientCompanyNM = recipientCompanyNM;
        this.recipientCeoNM = recipientCeoNM;
        this.recipientAddress = recipientAddress;
        this.totalPrice = totalPrice;
        this.supplyPrice = supplyPrice;
        this.taxAmount = taxAmount;
        this.invoiceClassify = invoiceClassify;
        this.invoiceType = invoiceType;
        this.issueType = issueType;
        this.priceNote = priceNote;
        this.classfication = classfication;
        this.email = email;
        this.recipientEmail1 = recipientEmail1;
        this.recipientEmail2 = recipientEmail2;
        this.itemDT = itemDT;
        this.itemNM = itemNM;
        this.itemSpec = itemSpec;
        this.itemQuantity = itemQuantity;
        this.itemUnitPrice = itemUnitPrice;
        this.itemSupplyPrice = itemSupplyPrice;
        this.itemTaxPrice = itemTaxPrice;
        this.itemNote = itemNote;
        this.collectionMoneyYN = collectionMoneyYN;
        this.collectionMoneyDt = collectionMoneyDt;
        this.paymentYN = paymentYN;
        this.paymentDt = paymentDt;
        this.note = note;
        this.salePurchaseClassification = salePurchaseClassification;
    }
}
