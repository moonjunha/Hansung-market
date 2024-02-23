package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.entity.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hansungmarket.demo.entity.user.QUser.user;

@RequiredArgsConstructor
@Repository
@Primary
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameCustom(String username) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .innerJoin(user.role).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByUsernameCustom(String username) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(user)
                .where(user.username.eq(username))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByNicknameCustom(String nickname) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(user)
                .where(user.nickname.eq(nickname))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    @Transactional
    public void updateIntroduceByIdCustom(Long id, String introduce) {
        jpaQueryFactory.update(user)
                .where(user.id.eq(id))
                .set(user.introduce, introduce)
                .execute();
    }

    @Override
    @Transactional
    public void updatePasswordByIdCustom(Long id, String password) {
        jpaQueryFactory.update(user)
                .where(user.id.eq(id))
                .set(user.password, password)
                .execute();
    }

    @Override
    @Transactional
    public void updatePasswordByAuthTokenCustom(String authToken, String password) {
        jpaQueryFactory.update(user)
                .where(user.authToken.eq(authToken))
                .set(user.password, password)
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserDtoByIdCustom(Long id) {
        return jpaQueryFactory.select(Projections.fields(UserDto.class,
                        user.id,
                        user.nickname,
                        user.username,
                        user.email,
                        user.introduce))
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<UserDto> findUserDtoListByIdCustom(List<Long> ids) {
        return jpaQueryFactory.select(Projections.fields(UserDto.class,
                        user.id,
                        user.nickname,
                        user.username,
                        user.email,
                        user.introduce))
                .from(user)
                .where(user.id.in(ids))
                .fetch();
    }

    @Override
    public List<String> findUsernameByEmailCustom(String email) {
        return jpaQueryFactory.select(user.username)
                .from(user)
                .where(user.email.eq(email))
                .fetch();
    }

}
