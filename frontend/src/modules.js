// Define el menú y los accesos visibles en la pantalla de inicio.
import {
  BookOpenText,
  CalendarDays,
  ChartNoAxesCombined,
  ClipboardCheck,
  FileUp,
  GraduationCap,
  LayoutGrid,
  MessagesSquare,
  UsersRound,
} from '@lucide/vue'

export const navigationItems = [
  { label: 'Inicio', icon: LayoutGrid, active: true },
  { label: 'Catálogo de temas', icon: BookOpenText, action: 'catalog' },
  { label: 'Mis postulaciones', icon: ClipboardCheck, action: 'applications' },
  { label: 'Entregas', icon: FileUp, upcoming: true },
  { label: 'Calendario', icon: CalendarDays, upcoming: true },
]

export const dashboardModules = [
  {
    number: '01',
    title: 'Catálogo de temas',
    description: 'Explora propuestas, compara áreas y encuentra un desafío que te represente.',
    icon: BookOpenText,
    accent: 'teal',
    action: 'catalog',
  },
  {
    number: '02',
    title: 'Mis postulaciones',
    description: 'Revisa el estado y la trazabilidad de tus solicitudes enviadas.',
    icon: ClipboardCheck,
    accent: 'orange',
    action: 'applications',
  },
  {
    number: '03',
    title: 'Entregas y avances',
    description: 'Carga documentos y revisa las observaciones de tu profesor guía.',
    icon: FileUp,
    accent: 'ink',
    upcoming: true,
  },
  {
    number: '04',
    title: 'Hitos académicos',
    description: 'Consulta fechas importantes y organiza tu proceso de titulación.',
    icon: CalendarDays,
    accent: 'lime',
    upcoming: true,
  },
  {
    number: '05',
    title: 'Equipo de tesis',
    description: 'Administra integrantes, profesor guía y co-guía de tu trabajo.',
    icon: UsersRound,
    accent: 'paper',
    upcoming: true,
  },
  {
    number: '06',
    title: 'Retroalimentación',
    description: 'Mantén en un solo lugar comentarios, correcciones y respuestas.',
    icon: MessagesSquare,
    accent: 'teal',
    upcoming: true,
  },
  {
    number: '07',
    title: 'Progreso de tesis',
    description: 'Visualiza tu avance general y los próximos pasos del proceso.',
    icon: ChartNoAxesCombined,
    accent: 'orange',
    upcoming: true,
  },
  {
    number: '08',
    title: 'Comisión evaluadora',
    description: 'Consulta integrantes, evaluaciones y resultado del proceso final.',
    icon: GraduationCap,
    accent: 'ink',
    upcoming: true,
  },
]
