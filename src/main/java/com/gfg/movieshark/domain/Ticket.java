package com.gfg.movieshark.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfg.movieshark.resource.TicketResource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "alloted_seats", nullable = false)
	private String allottedSeats;

	@Column(name = "amount", nullable = false)
	private double amount;

	@CreationTimestamp
	@Column(name = "booked_at", nullable = false)
	private Date bookedAt;

	@ManyToOne
	@JsonIgnore
	private User user;

	@ManyToOne
	@JsonIgnore
	private Show show;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ShowSeat> seats;


	public static List<TicketResource> toResource(List<Ticket> tickets){
		if(CollectionUtils.isEmpty(tickets))
			return new ArrayList<>();
		return tickets.stream().map(Ticket::toResource).collect(Collectors.toList());
	}

	public static Ticket toEntity(TicketResource ticketResource) {

		return Ticket.builder()
				.allottedSeats(ticketResource.getAllottedSeats())
				.amount(ticketResource.getAmount())
				.build();

	}

	public static TicketResource toResource(Ticket ticketEntity) {

		return TicketResource.builder()
				.id(ticketEntity.getId())
				.allottedSeats(ticketEntity.getAllottedSeats())
				.amount(ticketEntity.getAmount())
				.bookedAt(ticketEntity.getBookedAt())
				.build();
	}

}