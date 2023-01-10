package com.jinjin.jintranet.model.Qfile;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.jinjin.jintranet.model.NoticeAttach;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QNoticeAttach is a Querydsl query type for NoticeAttach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeAttach extends EntityPathBase<NoticeAttach> {

    private static final long serialVersionUID = -766882104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeAttach noticeAttach = new QNoticeAttach("noticeAttach");

    public final NumberPath<Integer> crtId = createNumber("crtId", Integer.class);

    public final DatePath<java.time.LocalDate> delDt = createDate("delDt", java.time.LocalDate.class);

    public final NumberPath<Integer> delId = createNumber("delId", Integer.class);

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QNotice notice;

    public final StringPath originalFileName = createString("originalFileName");

    public final StringPath path = createString("path");

    public final StringPath storedFileName = createString("storedFileName");

    public QNoticeAttach(String variable) {
        this(NoticeAttach.class, forVariable(variable), INITS);
    }

    public QNoticeAttach(Path<? extends NoticeAttach> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeAttach(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeAttach(PathMetadata metadata, PathInits inits) {
        this(NoticeAttach.class, metadata, inits);
    }

    public QNoticeAttach(Class<? extends NoticeAttach> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice"), inits.get("notice")) : null;
    }

}

