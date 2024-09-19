package com.Microservices.projectmanagementservice;

import com.Microservices.projectmanagementservice.client.TachesClient;
import com.Microservices.projectmanagementservice.model.Dto.ProjetsDTO;
import com.Microservices.projectmanagementservice.model.Entity.Projets;
import com.Microservices.projectmanagementservice.model.Entity.Statut;
import com.Microservices.projectmanagementservice.model.Entity.Taches;
import com.Microservices.projectmanagementservice.model.Mapper.ProjetsMapper;
import com.Microservices.projectmanagementservice.model.ProjetResponse;
import com.Microservices.projectmanagementservice.repository.ProjetsRepository;
import com.Microservices.projectmanagementservice.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

	@Mock
	private ProjetsRepository projetsRepository;

	@Mock
	private ProjetsMapper projetsMapper;

	@Mock
	private TachesClient tachesClient;

	@InjectMocks
	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllProjects() {
		Projets projet = new Projets(1L, "Projet 1", "Description du projet 1", new Date(), new Date(), 5000.0);
		ProjetsDTO projetDTO = new ProjetsDTO(1L, "Projet 1", "Description du projet 1",new Date(),new Date(), 5000.0);

		when(projetsRepository.findAll()).thenReturn(Arrays.asList(projet));
		when(projetsMapper.toDTO(projet)).thenReturn(projetDTO);

		List<ProjetsDTO> result = projectService.getAllProjects();
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(projetDTO, result.get(0));
	}

	@Test
	void testGetProjectById() {
		Projets projet = new Projets(1L, "Projet 1", "Description du projet 1", new Date(), new Date(), 5000.0);
		ProjetsDTO projetDTO = new ProjetsDTO(1L, "Projet 1", "Description du projet 1",new Date(),new Date(), 5000.0);

		when(projetsRepository.findById(1L)).thenReturn(Optional.of(projet));
		when(projetsMapper.toDTO(projet)).thenReturn(projetDTO);

		ProjetsDTO result = projectService.getProjectById(1L);
		assertNotNull(result);
		assertEquals(projetDTO, result);
	}

	@Test
	void testCreateProject() {
		Projets projet = new Projets(1L, "Projet 1", "Description du projet 1", new Date(), new Date(), 5000.0);
		ProjetsDTO projetDTO = new ProjetsDTO(1L, "Projet 1", "Description du projet 1",new Date(),new Date(), 5000.0);

		when(projetsMapper.toEntity(projetDTO)).thenReturn(projet);
		when(projetsRepository.save(projet)).thenReturn(projet);
		when(projetsMapper.toDTO(projet)).thenReturn(projetDTO);

		ProjetsDTO result = projectService.createProject(projetDTO);
		assertNotNull(result);
		assertEquals(projetDTO, result);
	}

	@Test
	void testUpdateProject() {
		Projets projet = new Projets(1L, "Projet 1", "Description du projet 1", new Date(), new Date(), 5000.0);
		ProjetsDTO projetDTO = new ProjetsDTO(1L, "Projet 1", "Description du projet 1",new Date(),new Date(), 5000.0);

		when(projetsRepository.findById(1L)).thenReturn(Optional.of(projet));
		when(projetsRepository.save(projet)).thenReturn(projet);
		when(projetsMapper.toDTO(projet)).thenReturn(projetDTO);

		ProjetsDTO result = projectService.updateProject(1L, projetDTO);
		assertNotNull(result);
		assertEquals(projetDTO, result);
	}

	@Test
	void testDeleteProject() {
		doNothing().when(projetsRepository).deleteById(1L);
		projectService.deleteProject(1L);
		verify(projetsRepository, times(1)).deleteById(1L);
	}


	@Test
	void testProjetWithTaches() {
		// Création d'un projet fictif
		Projets projet = new Projets(1L, "Projet 1", "Description 1", new Date(), new Date(), null);

		// Création d'une tâche fictive
		Date dateDebut = new Date(); // Date actuelle pour la date de début
		Date dateFin = new Date();   // Date actuelle pour la date de fin
		Taches tache = new Taches("Description Tache", dateDebut, dateFin, Statut.EN_COURS); // Exemple de statut

		// Simule une liste de tâches associée au projet
		List<Taches> taches = Arrays.asList(tache);

		// Simule la réponse du repository et du client pour la tâche
		when(projetsRepository.findById(1L)).thenReturn(Optional.of(projet));
		when(tachesClient.findAllTachesByProjet(1L)).thenReturn(taches);

		// Appel de la méthode à tester
		ProjetResponse result = projectService.projetWithTaches(1L);

		// Vérifications
		assertNotNull(result);
		assertEquals("Projet 1", result.getNom());  // Vérifie le nom du projet
		assertEquals(1, result.getTaches().size()); // Vérifie qu'il y a une tâche

		// Vérification des propriétés de la tâche
		Taches resultTache = result.getTaches().get(0);
		assertEquals("Description Tache", resultTache.getDescription());
		assertEquals(dateDebut, resultTache.getDateDebut());
		assertEquals(dateFin, resultTache.getDateFin());
		assertEquals(Statut.EN_COURS, resultTache.getStatut()); // Vérifie le statut
	}

}
