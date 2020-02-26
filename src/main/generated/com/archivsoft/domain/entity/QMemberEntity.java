package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = -2065697678L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final QTimeEntity _super = new QTimeEntity(this);

    public final StringPath approveYn = createString("approveYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> chgdt = _super.chgdt;

    public final StringPath email = createString("email");

    public final StringPath grade = createString("grade");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath ip = createString("ip");

    public final DateTimePath<java.util.Date> lastLoginDt = createDateTime("lastLoginDt", java.util.Date.class);

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdt = _super.regdt;

    public final StringPath useYn = createString("useYn");

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

