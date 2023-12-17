package dev.astranfalio.teioc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dev.astranfalio.teioc.service.DeleteInactiveInternsService;

@Component
public class DeleteInactiveInternsOnStartup implements CommandLineRunner {

    private final DeleteInactiveInternsService deleteInactiveInternsService;

    public DeleteInactiveInternsOnStartup(DeleteInactiveInternsService deleteInactiveInternsService) {
        this.deleteInactiveInternsService = deleteInactiveInternsService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Run the deletion logic on server startup
        deleteInactiveInternsService.deleteInactiveInterns();
    }
}

