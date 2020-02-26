/**
 * ========================================================
 *
 * @FileName : PurchaseDto.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.dto;

import com.archivsoft.domain.entity.StatisticEntity;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;

/**
 * 매출매입 데이터 교환 객체
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PurchaseDto {

    /**
     * 작성일자
     */
    private LocalDate regDt;
    /**
     * 고유 키값 승인번호
     */
    private String approveNum;
    /**
     * 발급일자
     */
    private LocalDate issueDt;
    /**
     * 전송일자
     */
    private LocalDate sendDt;
    /**
     * 공급자사업자등록번호
     */
    private String businessNum;
    /**
     * 종사업장번호
     */
    private String subBusinessNum;
    /**
     * 상호
     */
    private String companyNM;
    /**
     * 대표자명
     */
    private String ceoName;
    /**
     * 주소
     */
    private String address;
    /**
     * 공급받는자사업자등록번호
     */
    private String recipientBusinessNum;
    /**
     * 종사업장번호
     */
    private String recipientSubBusinessNum;
    /**
     * 상호
     */
    private String recipientCompanyNM;
    /**
     * 대표자명
     */
    private String recipientCeoNM;
    /**
     * 주소
     */
    private String recipientAddress;
    /**
     * 합계금액  공급가액 + 세액
     */
    private int totalPrice;
    /**
     * 공급가액 = 공급 단가
     */
    private int supplyPrice;
    /**
     * 세액 = 공급단가 의 0.1% ( 10% ) 단, 소수점은 반올림
     */
    private int taxAmount;
    /**
     * 전자세금계산서분류 세급계산서/수정세금계산서
     */
    private String invoiceClassify;
    /**
     * 전자세금계산서종류 일반, 일반(수정)
     */
    private String invoiceType;
    /**
     * 발급유형 인터넷발급/직접발급/ASP발급/겸용서식발급
     */
    private String issueType;
    /**
     * 비고
     */
    private String priceNote;
    /**
     * 영수/청구 구분
     */
    private String classfication;
    /**
     * 공급자 이메일
     */
    private String email;
    /**
     * 공급받는자 이메일1
     */
    private String recipientEmail1;
    /**
     * 공급받는자 이메일2
     */
    private String recipientEmail2;
    /**
     * 품목일자
     */
    private LocalDate itemDT;
    /**
     * 품목명
     */
    private String itemNM;
    /**
     * 품목규격
     */
    private String itemSpec;
    /**
     * 품목수량
     */
    private String itemQuantity;
    /**
     * 품목단가
     */
    private int itemUnitPrice;
    /**
     * 품목공급가액
     */
    private int itemSupplyPrice;
    /**
     * 품목세액
     */
    private int itemTaxPrice;
    /**
     * 품목비고
     */
    private String itemNote;
    /**
     * 수금여부
     */
    private String collectionMoneyYN;
    /**
     * 수금일자
     */
    private LocalDate collectionMoneyDt;
    /**
     * 지급여부
     */
    private String paymentYN;
    /**
     * 지급일
     */
    private LocalDate paymentDt;
    /**
     * 비고
     */
    private String note;
    /**
     * 매출/매입 구분
     * 공급자 사업번호가 862-86-00042 일경우 매출 아닐경우 매입 ( 매출 S/ 매입 P)
     */
    private String salePurchaseClassification;

    /**
     * StatisticEntity 전환 메소드
     */
    public StatisticEntity toEntity() {
        StatisticEntity build = StatisticEntity.builder()
                .regDt(regDt)
                .approveNum(approveNum)
                .issueDt(issueDt)
                .sendDt(sendDt)
                .businessNum(businessNum)
                .subBusinessNum(subBusinessNum)
                .companyNM(companyNM)
                .ceoName(ceoName)
                .address(address)
                .recipientBusinessNum(recipientBusinessNum)
                .recipientSubBusinessNum(recipientSubBusinessNum)
                .recipientCompanyNM(recipientCompanyNM)
                .recipientCeoNM(recipientCeoNM)
                .recipientAddress(recipientAddress)
                .totalPrice(totalPrice)
                .supplyPrice(supplyPrice)
                .taxAmount(taxAmount)
                .invoiceClassify(invoiceClassify)
                .invoiceType(invoiceType)
                .issueType(issueType)
                .priceNote(priceNote)
                .classfication(classfication)
                .approveNum(approveNum)
                .email(email)
                .recipientEmail1(recipientEmail1)
                .recipientEmail2(recipientEmail2)
                .itemDT(itemDT)
                .itemNM(itemNM)
                .itemSpec(itemSpec)
                .itemQuantity(itemQuantity)
                .itemUnitPrice(itemUnitPrice)
                .itemSupplyPrice(itemSupplyPrice)
                .itemTaxPrice(itemTaxPrice)
                .itemNote(itemNote)
                .collectionMoneyYN(collectionMoneyYN)
                .collectionMoneyDt(collectionMoneyDt)
                .paymentYN(paymentYN)
                .paymentDt(paymentDt)
                .note(note)
                .salePurchaseClassification(salePurchaseClassification)
                .build();

        return build;
    }

    /**
     * PurchaseDto 생성자
     */
    @Builder
    public PurchaseDto(LocalDate regDt, String approveNum, LocalDate issueDt, LocalDate sendDt, String businessNum, String subBusinessNum
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

    /**
     * row를 StatisticEntity 객체로 만드는 전환 메소드
     *
     * @param row
     * @history 2020 02 19 최초 생성
     */
    public static StatisticEntity toRow(Row row) {
        String row29 = row.getCell(29).getStringCellValue();
        int i29 = 0;
        if(row29.equals("")) {
            i29 = 0;
        } else {
            i29 = Integer.parseInt(row29.replaceAll(",",""));
        }
        return new PurchaseDto(
                LocalDate.parse(row.getCell(0).getStringCellValue())    // 작성일자
                , row.getCell(1).getStringCellValue()   // 승인번호
                , LocalDate.parse(row.getCell(2).getStringCellValue())  // 발급일자
                , LocalDate.parse(row.getCell(3).getStringCellValue())  // 전송일자
                , row.getCell(4).getStringCellValue()   // 공급자사업자등록번호
                , row.getCell(5).getStringCellValue()   // 종사업장번호
                , row.getCell(6).getStringCellValue()   // 상호
                , row.getCell(7).getStringCellValue()   // 대표자명
                , row.getCell(8).getStringCellValue()   // 주소
                , row.getCell(9).getStringCellValue()   // 공급받는자사업자등록번호
                , row.getCell(10).getStringCellValue()  // 종사업장번호
                , row.getCell(11).getStringCellValue()  // 상호
                , row.getCell(12).getStringCellValue()  // 대표자명
                , row.getCell(13).getStringCellValue()  // 주소
                , (int) row.getCell(14).getNumericCellValue()  // 합계금액
                , (int) row.getCell(15).getNumericCellValue()  // 공급가액
                , (int) row.getCell(16).getNumericCellValue()  // 세액
                , row.getCell(17).getStringCellValue()  // 전자세급계산서분류
                , row.getCell(18).getStringCellValue()  // 전자세금계산서종류
                , row.getCell(19).getStringCellValue()  // 발급유형
                , row.getCell(20).getStringCellValue()  // 비고
                , row.getCell(21).getStringCellValue()  // 영수 청구 구분
                , row.getCell(22).getStringCellValue()  // 공급자 이메일
                , row.getCell(23).getStringCellValue()  // 공급받는자 이메일1
                , row.getCell(24).getStringCellValue()  // 공급받는자 이메일2
                , LocalDate.parse(row.getCell(25).getStringCellValue()) //품목일자
                , row.getCell(26).getStringCellValue()  // 품목명
                , row.getCell(27).getStringCellValue()  // 품목규격
                , row.getCell(28).getStringCellValue()  // 품목수량
                , i29  // 품목단가
                , (int) row.getCell(30).getNumericCellValue()   // 품목공급가액
                , (int) row.getCell(31).getNumericCellValue()  // 품목세액
                , row.getCell(32).getStringCellValue()  // 품목비고
                , ""  // 수금여부
                , null // 수금일자
                , "" // 지급여부
                , null   // 지급일
                , row.getCell(35).getStringCellValue()  // 비고
                , "P"   // 매출 매입 구분
        ).toEntity();
    }
}
