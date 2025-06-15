package com.quest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.quest.interfaces.rest.IBoardServices;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.interfaces.rest.IThemeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

@Component
//@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final IPlayerServices playerService;
    private final IBoardServices boardService;
    private final IThemeServices themeService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final String JSON_PATH = "json/";

    @Autowired
    public DataInitializer(IPlayerServices playerService,
                           IBoardServices boardService,
                           IThemeServices themeService) {
        this.playerService = playerService;
        this.boardService = boardService;
        this.themeService = themeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(3000);
        /*loadData("membros.json", MembroCreateDTO[].class,
                membroQueryService.count(), membroCommandService::create);
        loadData("eventos.json", EventoCreateDTO[].class,
                eventoQueryService.count(), eventoCommandService::create);
        loadData("recursos.json", RecursoCreateDTO[].class,
                recursoQueryService.count(), recursoCommandService::create);
        loadData("inscricoes.json", InscricaoCreateDTO[].class,
                inscricaoQueryService.count(), inscricaoCommandService::create);
        loadData("mensalidades.json", MensalidadeCreateDTO[].class,
                mensalidadeQueryService.count(), mensalidadeCommandService::create);*/
    }

    private <T> void loadData(String fileName, Class<T[]> clazz, long count, Consumer<T> saveFn) {
        if (count > 0) {
            System.out.println("Dados já existem para: " + clazz.getSimpleName());
            return;
        }

        try {
            Resource resource = new ClassPathResource(JSON_PATH + fileName);
            try (InputStream inputStream = resource.getInputStream()) {
                List<T> data = List.of(objectMapper.readValue(inputStream, clazz));
                data.forEach(saveFn);
                System.out.println(data.size() + " registros inseridos de " + clazz.getSimpleName());
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
