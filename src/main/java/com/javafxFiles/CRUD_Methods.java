package com.javafxFiles;

import com.models.Appliance;

public sealed interface CRUD_Methods permits ApplianceController, CategoryController {
    void add();
    void search();
    void change();
    void delete();
}
