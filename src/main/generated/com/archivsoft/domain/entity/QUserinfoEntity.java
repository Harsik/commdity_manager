package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserinfoEntity is a Querydsl query type for UserinfoEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserinfoEntity extends EntityPathBase<UserinfoEntity> {

    private static final long serialVersionUID = -1063331759L;

    public static final QUserinfoEntity userinfoEntity = new QUserinfoEntity("userinfoEntity");

    public final QTimeEntity _super = new QTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> chgdt = _super.chgdt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdt = _super.regdt;

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public final StringPath userName = createString("userName");

    public final StringPath userPosition = createString("userPosition");

    public final DatePath<java.time.LocalDate> userRegdt = createDate("userRegdt", java.time.LocalDate.class);

    public final StringPath userUseYN = createString("userUseYN");

    public QUserinfoEntity(String variable) {
        super(UserinfoEntity.class, forVariable(variable));
    }

    public QUserinfoEntity(Path<? extends UserinfoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserinfoEntity(PathMetadata metadata) {
        super(UserinfoEntity.class, metadata);
    }

}

