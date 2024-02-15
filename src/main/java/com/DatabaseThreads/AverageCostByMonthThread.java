package com.DatabaseThreads;

import com.javafxFiles.HelloApplication;
import com.javafxFiles.LoginController;
import com.models.Months;
import com.models.Role;
import com.models.RoleByMonths;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.List;

public class AverageCostByMonthThread implements Runnable{
    @Override
    public void run() {
        Role role = LoginController.currentUser;
        RoleByMonths<Role, List<Months>> genericnaKlasa = new RoleByMonths<>(role, Arrays.stream(Months.values()).toList());
        Platform.runLater(() -> {
            HelloApplication.getStage().setTitle(
                    "Za korisnika " + genericnaKlasa.getRole().getUsername() + " prosječni račun po mjesecu iznosi " + genericnaKlasa.getAverageBill()
            );
        });
    }
}

