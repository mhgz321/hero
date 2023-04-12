package com.keaizhale.hero.satoken.dao;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;

import java.util.List;

/**
 * description: SaTokenDaoImpl
 * date: 2023/4/6 10:42
 * author: keaizhale
 * version: 1.0
 */
public class SaTokenDaoImpl implements SaTokenDao {
    @Override
    public SaSession getSession(String sessionId) {
        return SaTokenDao.super.getSession(sessionId);
    }

    @Override
    public void setSession(SaSession session, long timeout) {
        SaTokenDao.super.setSession(session, timeout);
    }

    @Override
    public void updateSession(SaSession session) {
        SaTokenDao.super.updateSession(session);
    }

    @Override
    public void deleteSession(String sessionId) {
        SaTokenDao.super.deleteSession(sessionId);
    }

    @Override
    public long getSessionTimeout(String sessionId) {
        return SaTokenDao.super.getSessionTimeout(sessionId);
    }

    @Override
    public void updateSessionTimeout(String sessionId, long timeout) {
        SaTokenDao.super.updateSessionTimeout(sessionId, timeout);
    }

    @Override
    public String get(String s) {
        return null;
    }

    @Override
    public void set(String s, String s1, long l) {

    }

    @Override
    public void update(String s, String s1) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public long getTimeout(String s) {
        return 0;
    }

    @Override
    public void updateTimeout(String s, long l) {

    }

    @Override
    public Object getObject(String s) {
        return null;
    }

    @Override
    public void setObject(String s, Object o, long l) {

    }

    @Override
    public void updateObject(String s, Object o) {

    }

    @Override
    public void deleteObject(String s) {

    }

    @Override
    public long getObjectTimeout(String s) {
        return 0;
    }

    @Override
    public void updateObjectTimeout(String s, long l) {

    }

    @Override
    public List<String> searchData(String s, String s1, int i, int i1, boolean b) {
        return null;
    }
}
