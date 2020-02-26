package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStatisticEntity is a Querydsl query type for StatisticEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStatisticEntity extends EntityPathBase<StatisticEntity> {

    private static final long serialVersionUID = 894770142L;

    public static final QStatisticEntity statisticEntity = new QStatisticEntity("statisticEntity");

    public final StringPath address = createString("address");

    public final StringPath approveNum = createString("approveNum");

    public final StringPath businessNum = createString("businessNum");

    public final StringPath ceoName = createString("ceoName");

    public final StringPath classfication = createString("classfication");

    public final DatePath<java.time.LocalDate> collectionMoneyDt = createDate("collectionMoneyDt", java.time.LocalDate.class);

    public final StringPath collectionMoneyYN = createString("collectionMoneyYN");

    public final StringPath companyNM = createString("companyNM");

    public final StringPath email = createString("email");

    public final StringPath invoiceClassify = createString("invoiceClassify");

    public final StringPath invoiceType = createString("invoiceType");

    public final DatePath<java.time.LocalDate> issueDt = createDate("issueDt", java.time.LocalDate.class);

    public final StringPath issueType = createString("issueType");

    public final DatePath<java.time.LocalDate> itemDT = createDate("itemDT", java.time.LocalDate.class);

    public final StringPath itemNM = createString("itemNM");

    public final StringPath itemNote = createString("itemNote");

    public final StringPath itemQuantity = createString("itemQuantity");

    public final StringPath itemSpec = createString("itemSpec");

    public final NumberPath<Integer> itemSupplyPrice = createNumber("itemSupplyPrice", Integer.class);

    public final NumberPath<Integer> itemTaxPrice = createNumber("itemTaxPrice", Integer.class);

    public final NumberPath<Integer> itemUnitPrice = createNumber("itemUnitPrice", Integer.class);

    public final StringPath note = createString("note");

    public final DatePath<java.time.LocalDate> paymentDt = createDate("paymentDt", java.time.LocalDate.class);

    public final StringPath paymentYN = createString("paymentYN");

    public final StringPath priceNote = createString("priceNote");

    public final StringPath recipientAddress = createString("recipientAddress");

    public final StringPath recipientBusinessNum = createString("recipientBusinessNum");

    public final StringPath recipientCeoNM = createString("recipientCeoNM");

    public final StringPath recipientCompanyNM = createString("recipientCompanyNM");

    public final StringPath recipientEmail1 = createString("recipientEmail1");

    public final StringPath recipientEmail2 = createString("recipientEmail2");

    public final StringPath recipientSubBusinessNum = createString("recipientSubBusinessNum");

    public final DatePath<java.time.LocalDate> regDt = createDate("regDt", java.time.LocalDate.class);

    public final StringPath salePurchaseClassification = createString("salePurchaseClassification");

    public final DatePath<java.time.LocalDate> sendDt = createDate("sendDt", java.time.LocalDate.class);

    public final StringPath subBusinessNum = createString("subBusinessNum");

    public final NumberPath<Integer> supplyPrice = createNumber("supplyPrice", Integer.class);

    public final NumberPath<Integer> taxAmount = createNumber("taxAmount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QStatisticEntity(String variable) {
        super(StatisticEntity.class, forVariable(variable));
    }

    public QStatisticEntity(Path<? extends StatisticEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStatisticEntity(PathMetadata metadata) {
        super(StatisticEntity.class, metadata);
    }

}

