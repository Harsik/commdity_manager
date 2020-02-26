package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryEntity is a Querydsl query type for CategoryEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCategoryEntity extends EntityPathBase<CategoryEntity> {

    private static final long serialVersionUID = -1730557290L;

    public static final QCategoryEntity categoryEntity = new QCategoryEntity("categoryEntity");

    public final QTimeEntity _super = new QTimeEntity(this);

    public final StringPath cateCompany = createString("cateCompany");

    public final StringPath cateCredit = createString("cateCredit");

    public final NumberPath<Long> cateIdx = createNumber("cateIdx", Long.class);

    public final StringPath cateItem = createString("cateItem");

    public final StringPath cateModelName = createString("cateModelName");

    public final StringPath cateNature = createString("cateNature");

    public final StringPath cateRegNM = createString("cateRegNM");

    public final StringPath cateSection = createString("cateSection");

    public final StringPath cateSubject = createString("cateSubject");

    public final StringPath cateType = createString("cateType");

    public final StringPath cateUse = createString("cateUse");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> chgdt = _super.chgdt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdt = _super.regdt;

    public QCategoryEntity(String variable) {
        super(CategoryEntity.class, forVariable(variable));
    }

    public QCategoryEntity(Path<? extends CategoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryEntity(PathMetadata metadata) {
        super(CategoryEntity.class, metadata);
    }

}

