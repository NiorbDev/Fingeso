package pgt.config;

import pgt.domain.ThesisTopic;
import pgt.domain.UserAccount;
import pgt.domain.UserRole;
import pgt.repository.ThesisTopicRepository;
import pgt.repository.UserAccountRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoDataSeeder {
    @Bean
    CommandLineRunner seedDemoData(UserAccountRepository users, ThesisTopicRepository topics,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (users.findByEmailIgnoreCase("estudiante@usach.cl").isEmpty()) {
                users.save(new UserAccount("Martina Rojas Soto", "estudiante@usach.cl",
                        passwordEncoder.encode("usach2026"), UserRole.STUDENT,
                        "Ingeniería Civil Informática"));
            }
            if (topics.count() == 0) topics.saveAll(demoTopics());
        };
    }

    private List<ThesisTopic> demoTopics() {
        String department = "Departamento de Ingeniería Informática";
        return List.of(
                new ThesisTopic("INF-26-014", "Detección temprana de incendios mediante visión computacional",
                        "Diseño de un sistema que analice imágenes en tiempo real para anticipar focos de incendio en zonas de interfaz urbano-forestal.",
                        "El proyecto propone construir y evaluar un prototipo de visión computacional capaz de identificar columnas de humo y patrones térmicos en imágenes capturadas desde puntos de observación. El trabajo combinará preparación de datos, evaluación de modelos y una interfaz de monitoreo orientada a la toma de decisiones.",
                        "Inteligencia Artificial", "Dra. Camila Arancibia", "camila.arancibia@usach.cl", department,
                        2, 0, "Individual o grupal", "12 horas semanales", "Laboratorio de Computación Visual", true,
                        LocalDate.of(2026, 8, 12), List.of("Python", "PyTorch", "OpenCV", "Vue"),
                        List.of("Construir un conjunto de datos representativo para el contexto nacional.",
                                "Comparar arquitecturas de detección y segmentación bajo restricciones de tiempo real.",
                                "Diseñar un panel de monitoreo que comunique alertas con claridad."),
                        List.of("Programación intermedia en Python.",
                                "Interés por aprendizaje automático y procesamiento de imágenes.",
                                "Disponibilidad para reuniones semanales de seguimiento.")),
                new ThesisTopic("INF-26-009", "Accesibilidad digital para servicios universitarios",
                        "Auditoría y rediseño de trámites digitales críticos utilizando WCAG y pruebas participativas con estudiantes.",
                        "La propuesta busca identificar barreras de acceso en servicios universitarios y desarrollar patrones de interfaz verificables, reutilizables y alineados con estándares internacionales de accesibilidad.",
                        "Interacción Humano-Computador", "Mg. Sebastián Mella", "sebastian.mella@usach.cl", department,
                        2, 1, "Grupal", "10 horas semanales", "Laboratorio de Experiencia de Usuario", false,
                        LocalDate.of(2026, 8, 9), List.of("Vue", "Playwright", "Figma", "ARIA"),
                        List.of("Caracterizar barreras en una muestra de trámites universitarios.",
                                "Diseñar y validar componentes accesibles reutilizables.",
                                "Documentar recomendaciones técnicas y organizacionales."),
                        List.of("Conocimientos de desarrollo web.", "Interés por diseño centrado en las personas.")),
                new ThesisTopic("INF-26-021", "Optimización de recorridos para logística de última milla",
                        "Modelos heurísticos para mejorar la planificación de entregas urbanas considerando ventanas horarias y carga vehicular.",
                        "Se estudiarán estrategias de optimización combinatoria aplicadas a una red urbana dinámica, construyendo un simulador que permita comparar resultados y visualizar rutas.",
                        "Ciencia de Datos", "Dr. Rodrigo Álvarez", "rodrigo.alvarez@usach.cl", department,
                        1, 0, "Individual", "12 horas semanales", "Laboratorio de Sistemas Inteligentes", false,
                        LocalDate.of(2026, 8, 8), List.of("Python", "PostgreSQL", "OR-Tools", "MapLibre"),
                        List.of("Formalizar las restricciones del problema.", "Comparar heurísticas en escenarios simulados.",
                                "Construir una herramienta visual de análisis."),
                        List.of("Investigación de operaciones básica.", "Programación en Python o lenguaje equivalente.")),
                new ThesisTopic("INF-26-018", "Seguridad y trazabilidad en cadenas de suministro alimentarias",
                        "Arquitectura distribuida para registrar eventos críticos y verificar el origen de productos agroalimentarios.",
                        "El trabajo contempla el diseño de una arquitectura de eventos que mantenga trazabilidad verificable desde el productor hasta el consumidor.",
                        "Sistemas Distribuidos", "Dra. Valentina Soto", "valentina.soto@usach.cl", department,
                        2, 0, "Individual o grupal", "10 horas semanales", "Laboratorio de Sistemas Distribuidos", false,
                        LocalDate.of(2026, 8, 5), List.of("Java", "Spring Boot", "PostgreSQL", "Kafka"),
                        List.of("Definir eventos y actores de la cadena.", "Implementar un prototipo auditable.",
                                "Evaluar rendimiento y consistencia."),
                        List.of("Programación orientada a objetos.", "Fundamentos de bases de datos.")),
                new ThesisTopic("INF-26-003", "Analítica de aprendizaje para cursos de primer año",
                        "Indicadores explicables para detectar tempranamente dificultades académicas y orientar acciones de acompañamiento.",
                        "La tesis explorará datos históricos anonimizados para proponer indicadores tempranos, priorizando explicabilidad, privacidad y uso responsable.",
                        "Ciencia de Datos", "Mg. Paula Contreras", "paula.contreras@usach.cl", department,
                        2, 1, "Grupal", "10 horas semanales", "Modalidad híbrida", false,
                        LocalDate.of(2026, 8, 2), List.of("Python", "Pandas", "PostgreSQL", "Metabase"),
                        List.of("Definir indicadores accionables.", "Evaluar modelos explicables.",
                                "Prototipar un tablero para equipos docentes."),
                        List.of("Estadística básica.", "Manejo responsable de información sensible.")));
    }
}

