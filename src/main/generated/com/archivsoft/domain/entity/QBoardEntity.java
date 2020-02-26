package com.archivsoft.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardEntity is a Querydsl query type for BoardEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardEntity extends EntityPathBase<BoardEntity> {

    private static final long serialVersionUID = 608274036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardEntity boardEntity = new QBoardEntity("boardEntity");

    public final QTimeEntity _super = new QTimeEntity(this);

    public final DatePath<java.time.LocalDate> boardCarryinDt = createDate("boardCarryinDt", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> boardCarryoutDt = createDate("boardCarryoutDt", java.time.LocalDate.class);

    public final StringPath boardCarryYN = createString("boardCarryYN");

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final StringPath boardUsePlace = createString("boardUsePlace");

    public final NumberPath<Long> boardUserIdx = createNumber("boardUserIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> chgdt = _super.chgdt;

    public final QProductEntity productEntity;

    public final NumberPath<Long> productIdx = createNumber("productIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdt = _super.regdt;

    public final QUserinfoEntity userinfoEntity;

    public QBoardEntity(String variable) {
        this(BoardEntity.class, forVariable(variable), INITS);
    }

    public QBoardEntity(Path<? extends BoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardEntity(PathMetadata metadata, PathInits inits) {
        this(BoardEntity.class, metadata, inits);
    }

    public QBoardEntity(Class<? extends BoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productEntity = inits.isInitialized("productEntity") ? new QProductEntity(forProperty("productEntity")) : null;
        this.userinfoEntity = inits.isInitialized("userinfoEntity") ? new QUserinfoEntity(forProperty("userinfoEntity")) : null;
    }

}

