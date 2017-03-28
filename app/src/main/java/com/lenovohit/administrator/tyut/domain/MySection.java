package com.lenovohit.administrator.tyut.domain;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.lenovohit.administrator.tyut.greendao.Score;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MySection extends SectionEntity<Score>{
    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }
    public MySection(Score score){
        super(score);
    }
}
