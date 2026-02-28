package app.service;

import app.dao.LanguageDAO;
import app.entity.Language;
import app.enums.LanguageEnum;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageService extends EntityManagerService<Language> {

    // Attributes
    private final LanguageDAO languageDAO;
    private static Map<LanguageEnum, Language> languageEnumCache = new HashMap<>();
    private static Map<String, Language> iso639Cache = new HashMap<>();
    private static Map<String, Language> iso3166Cache = new HashMap<>();
    private static boolean cacheLoaded = false;

    // _________________________________________________________

    public LanguageService(EntityManager em) {
        super(new LanguageDAO(em), Language.class);
        this.languageDAO = (LanguageDAO) this.entityManagerDAO;
    }

    // _________________________________________________________
    // synchronized used to prevent multi threading on loadCache

    private synchronized void loadCacheIfNeeded() {
        if (cacheLoaded) return;
        List<Language> allLanguages = languageDAO.getAll();
        for (Language lang : allLanguages) {
            languageEnumCache.put(lang.getLanguageEnum(), lang);
            iso639Cache.put(lang.getLanguageEnum().getIso639(), lang);
            iso3166Cache.put(lang.getLanguageEnum().getIso3166(), lang);
        }
        cacheLoaded = true;
    }

    // _________________________________________________________

    public Language findLanguageByEnum(LanguageEnum languageEnum) {
        return languageEnumCache.get(languageEnum);
    }

    // _________________________________________________________

    public Language findLanguageByIso639(String iso639) {
        return iso639Cache.get(iso639);
    }

    // _________________________________________________________

    public Language findLanguageByIso3166(String iso3166) {
        return iso3166Cache.get(iso3166);
    }

}