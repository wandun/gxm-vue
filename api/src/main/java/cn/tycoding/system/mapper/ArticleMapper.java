package cn.tycoding.system.mapper;

import cn.tycoding.system.entity.SysArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author TyCoding
 * @date 2018/10/16
 */
@Component
public interface ArticleMapper extends BaseMapper<SysArticle> {

    /**
     * 查询所有问文章总数
     * @return
     */
    Integer findAllArticleNum();

    List<String> findArchivesDates();

    List<SysArticle> findArchivesByDate(String date);
}
