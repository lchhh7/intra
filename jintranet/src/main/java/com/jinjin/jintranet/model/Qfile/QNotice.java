package com.jinjin.jintranet.model.Qfile;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.jinjin.jintranet.model.Notice;
import com.jinjin.jintranet.model.NoticeAttach;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -1102955869L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotice notice = new QNotice("notice");

    public final ListPath<NoticeAttach, QNoticeAttach> attaches = this.<NoticeAttach, QNoticeAttach>createList("attaches", NoticeAttach.class, QNoticeAttach.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> crtDt = createDate("crtDt", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> delDt = createDate("delDt", java.time.LocalDate.class);

    public final NumberPath<Integer> delId = createNumber("delId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> postEndDt = createDateTime("postEndDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> postStrDt = createDateTime("postStrDt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final DatePath<java.time.LocalDate> udtDt = createDate("udtDt", java.time.LocalDate.class);

    public QNotice(String variable) {
        this(Notice.class, forVariable(variable), INITS);
    }

    public QNotice(Path<? extends Notice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotice(PathMetadata metadata, PathInits inits) {
        this(Notice.class, metadata, inits);
    }

    public QNotice(Class<? extends Notice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

