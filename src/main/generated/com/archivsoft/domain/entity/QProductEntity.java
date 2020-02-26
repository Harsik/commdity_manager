package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = -149085443L;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final QTimeEntity _super = new QTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> chgdt = _super.chgdt;

    public final DatePath<java.time.LocalDate> productBuyDt = createDate("productBuyDt", java.time.LocalDate.class);

    public final StringPath productCompany = createString("productCompany");

    public final NumberPath<Long> productIdx = createNumber("productIdx", Long.class);

    public final StringPath productItem = createString("productItem");

    public final StringPath productManagerNum = createString("productManagerNum");

    public final StringPath productModelName = createString("productModelName");

    public final StringPath productSerial = createString("productSerial");

    public final StringPath productStatus = createString("productStatus");

    public final StringPath productUseYN = createString("productUseYN");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdt = _super.regdt;

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }

}

