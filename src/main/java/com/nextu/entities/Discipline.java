package com.nextu.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discipline")
public class Discipline {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "code_discipline")
   private Long code;
   @Column(name = "intitule")
   private String libelle;
   @JoinColumn(name = "code_sport")
   @ManyToOne(optional = false, cascade = CascadeType.ALL)
   private Sport sport;

   public Long getCode() {
      return code;
   }

   public void setCode(Long code) {
      this.code = code;
   }

   public String getLibelle() {
      return libelle;
   }

   public void setLibelle(String libelle) {
      this.libelle = libelle;
   }

   public Sport getSport() {
      return sport;
   }

   public void setSport(Sport sport) {
      this.sport = sport;
   }

   public void addSport(Sport sport) {
      this.sport = sport;
      sport.getDisciplines().add(this);
   }

}
