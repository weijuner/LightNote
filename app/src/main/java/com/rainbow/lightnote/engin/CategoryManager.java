package com.rainbow.lightnote.engin;

import com.rainbow.lightnote.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijuner on 2015/9/9.
 */
public class CategoryManager {
    public Category getCategory(){
        Category cato1 = new Category(1,"关于不见");
        Category cato2 = new Category(1,"关于不散");
        return cato1;
    }
    public List<Category> getCategorys(){
        List<Category> categorys = new ArrayList();
        Category cato1 = new Category(1,"关于不见");
        Category cato2 = new Category(1,"关于不散");
        categorys.add(cato1);
        categorys.add(cato2);
        return categorys;
    }
}
