package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.hansungmarket.demo.entity.board.QBoard.board;
import static com.hansungmarket.demo.entity.board.QLikeBoard.likeBoard;

@RequiredArgsConstructor
@Repository
@Primary
public class LikeBoardRepositoryImpl implements LikeBoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Board> findByUserIdCustom(Long id, int page) {
        long pageSize = 12L;

        // offset 설정을 위해 -1
        page--;

        // 페이징에 필요한 board id만 추출출
        List<Long> ids = jpaQueryFactory.select(board.id).from(board)
                .join(likeBoard).on(board.id.eq(likeBoard.board.id), likeBoard.user.id.eq(id))
                .orderBy(board.createdDateTime.desc())
                .offset(page * pageSize)
                .limit(pageSize)
                .fetch();

        // ids 가 비어있으면 바로 리턴
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        // ids 에 존재하는 값만 select
        return jpaQueryFactory.selectFrom(board)
                .where(board.id.in(ids))
                .orderBy(board.createdDateTime.desc())
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByBoardIdAndUserIdCustom(Long boardId, Long userId) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(likeBoard)
                .where(likeBoard.board.id.eq(boardId), likeBoard.user.id.eq(userId))
                .fetchFirst();

        return fetchOne != null;
    }
}
